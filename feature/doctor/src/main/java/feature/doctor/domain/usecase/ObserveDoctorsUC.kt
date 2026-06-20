package feature.doctor.domain.usecase

import androidx.paging.PagingData
import core.common.model.Doctor
import feature.doctor.domain.repository.DoctorRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveDoctorsUC @Inject constructor(
    private val repo: DoctorRepository
) {
    @OptIn(FlowPreview::class)
    operator fun invoke(specialtyId: Long, query: String): Flow<PagingData<Doctor>> =
        repo.observeDoctors(specialtyId, query)
}
