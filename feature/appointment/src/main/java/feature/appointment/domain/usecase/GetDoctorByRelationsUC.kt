package feature.appointment.domain.usecase

import core.common.model.Doctor
import feature.appointment.domain.repository.AppointmentRepository
import javax.inject.Inject

class GetDoctorByRelationsUC @Inject constructor(
    private val repo: AppointmentRepository
) {
    suspend operator fun invoke(doctorId: Long): Doctor =
        repo.getDoctorAndSpecialties(doctorId)
}