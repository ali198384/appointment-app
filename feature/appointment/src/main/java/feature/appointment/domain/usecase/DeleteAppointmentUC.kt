package feature.appointment.domain.usecase

import feature.appointment.domain.repository.AppointmentRepository
import javax.inject.Inject

class DeleteAppointmentUC @Inject constructor(
    private val repo: AppointmentRepository
) {
    suspend operator fun invoke(trackingCode: String, timeSlotId: Long) =
        repo.markAppointmentDeleted(trackingCode, timeSlotId)
}