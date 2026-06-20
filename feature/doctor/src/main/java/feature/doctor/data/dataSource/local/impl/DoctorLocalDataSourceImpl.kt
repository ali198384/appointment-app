package feature.doctor.data.dataSource.local.impl

import androidx.paging.PagingSource
import core.database.local.dao.DoctorDao
import core.database.local.entity.DoctorEntity
import core.database.local.entity.DoctorSpecialtyCrossRef
import core.database.local.relation.DoctorWithRelations
import feature.doctor.data.dataSource.local.DoctorLocalDataSource
import javax.inject.Inject

class DoctorLocalDataSourceImpl @Inject constructor(
    private val dao: DoctorDao
) : DoctorLocalDataSource {

    override fun getDoctorsPaging(
        specialtyId: Long,
        query: String
    ): PagingSource<Int, DoctorEntity> =
        dao.getDoctorsPaging(specialtyId, query)

    override suspend fun getDoctorWithRelations(doctorId: Long): DoctorWithRelations =
        dao.getDoctorWithRelations(doctorId)

    override suspend fun insertDoctors(
        doctors: List<DoctorEntity>,
        refs: List<DoctorSpecialtyCrossRef>
    ) {
        dao.insertDoctors(doctors)
        dao.insertCrossRefs(refs)
    }

    override suspend fun clearDoctors() {
        dao.clearDoctors()
    }
}