package feature.doctor.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import core.database.local.db.AppDatabase
import core.database.local.entity.DoctorEntity
import feature.doctor.data.dataSource.local.DoctorLocalDataSource
import feature.doctor.data.dataSource.remote.DoctorRemoteDataSource
import feature.doctor.data.mapper.toCrossRefs
import feature.doctor.data.mapper.toEntity


@OptIn(ExperimentalPagingApi::class)
class DoctorRemoteMediator(
    private val specialtyId: Long,
    private val query: String,
    private val local: DoctorLocalDataSource,
    private val remote: DoctorRemoteDataSource,
    private val db: AppDatabase
) : RemoteMediator<Int, DoctorEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DoctorEntity>
    ): MediatorResult = try {

        // بدون سرور
        /*return MediatorResult.Success(
            endOfPaginationReached = true
        )*/

        // با سرور
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.APPEND -> state.pages.size + 1
            LoadType.PREPEND -> return MediatorResult.Success(true)
        }

        val remoteData = remote.fetchDoctors(
            page,
            state.config.pageSize,
            specialtyId,
            query,
        )

        db.withTransaction {

            if (loadType == LoadType.REFRESH)
                local.clearDoctors()

            local.insertDoctors(
                remoteData.map { it.toEntity() },
                remoteData.flatMap { it.toCrossRefs() }
            )
        }

        MediatorResult.Success(remoteData.isEmpty())

    } catch (e: Exception) {
        MediatorResult.Error(e)
    }
}
