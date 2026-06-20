package feature.appointment.domain.repository

import core.common.model.Appointment
import core.common.model.Doctor
import core.common.model.Patient
import core.common.model.Profile
import core.common.model.TimeSlot

interface AppointmentRepository {
    suspend fun getDoctorAndSpecialties(doctorId: Long): Doctor
    suspend fun getSlots(doctorId: Long): List<TimeSlot>
    suspend fun getProfile(): Profile?
    suspend fun createAppointment(timeSlotId: Long, patient: Patient): String
    suspend fun syncIfNeeded()
    suspend fun getAppointmentByTrackingCode(trackingCode: String): Appointment?
    suspend fun markAppointmentDeleted(trackingCode: String, timeSlotId: Long)
}
