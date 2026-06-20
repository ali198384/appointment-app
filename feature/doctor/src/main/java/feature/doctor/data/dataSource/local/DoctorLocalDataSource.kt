package feature.doctor.data.dataSource.local

import androidx.paging.PagingSource
import core.database.local.entity.DoctorEntity
import core.database.local.entity.DoctorSpecialtyCrossRef
import core.database.local.relation.DoctorWithRelations

interface DoctorLocalDataSource {
    fun getDoctorsPaging(specialtyId: Long, query: String): PagingSource<Int, DoctorEntity>
    suspend fun getDoctorWithRelations(doctorId: Long): DoctorWithRelations
    suspend fun insertDoctors(doctors: List<DoctorEntity>, refs: List<DoctorSpecialtyCrossRef>)
    suspend fun clearDoctors()
}