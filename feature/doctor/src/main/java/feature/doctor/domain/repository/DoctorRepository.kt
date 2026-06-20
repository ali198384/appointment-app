package feature.doctor.domain.repository

import androidx.paging.PagingData
import core.common.model.Doctor
import kotlinx.coroutines.flow.Flow

interface DoctorRepository {
    fun observeDoctors(specialtyId: Long, query: String): Flow<PagingData<Doctor>>
}
