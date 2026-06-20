package feature.appointment.domain.usecase

import core.common.model.Patient
import feature.appointment.domain.repository.AppointmentRepository
import javax.inject.Inject

class CreateAppointmentUC @Inject constructor(
    private  val repo: AppointmentRepository
){
    suspend operator fun invoke(
        timeSlotId: Long,
        patient: Patient
    ): String = repo.createAppointment(timeSlotId, patient)
}