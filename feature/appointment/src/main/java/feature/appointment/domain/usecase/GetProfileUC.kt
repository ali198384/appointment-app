package feature.appointment.domain.usecase

import feature.appointment.domain.repository.AppointmentRepository
import javax.inject.Inject

class GetProfileUC @Inject constructor(
    private val repo: AppointmentRepository
) {
    suspend operator fun invoke() = repo.getProfile()
}