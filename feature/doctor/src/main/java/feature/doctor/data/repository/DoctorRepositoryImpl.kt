package feature.doctor.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import core.common.model.Doctor
import core.database.local.db.AppDatabase
import feature.doctor.data.dataSource.local.DoctorLocalDataSource
import feature.doctor.data.dataSource.remote.DoctorRemoteDataSource
import feature.doctor.data.mapper.toDomain
import feature.doctor.data.paging.DoctorRemoteMediator
import feature.doctor.domain.repository.DoctorRepository
import feature.doctor.domain.repository.DoctorSharedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val local: DoctorLocalDataSource,
    private val remote: DoctorRemoteDataSource
) : DoctorRepository, DoctorSharedRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun observeDoctors(
        specialtyId: Long,
        query: String
    ): Flow<PagingData<Doctor>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = DoctorRemoteMediator(
                specialtyId,
                query,
                local,
                remote,
                db
            ),
            pagingSourceFactory = {
                local.getDoctorsPaging(specialtyId, query)
            }
        ).flow.map { paging ->
            paging.map { entity ->
                local.getDoctorWithRelations(entity.id).toDomain()
            }.filter {
                it.nextAvailableTimeSlot != null
            }
        }

    override suspend fun getDoctorWithRelations(doctorId: Long): Doctor =
        local.getDoctorWithRelations(doctorId).toDomain()
}
