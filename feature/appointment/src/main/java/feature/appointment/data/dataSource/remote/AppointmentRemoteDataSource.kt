package feature.appointment.data.dataSource.remote

import core.network.dto.AppointmentDto
import core.network.dto.TimeSlotDto

interface AppointmentRemoteDataSource {
    suspend fun getTimeSlots(doctorId: Long): List<TimeSlotDto>
    suspend fun createAppointment(body: AppointmentDto): String?
    suspend fun deleteAppointment(trackingCode: String): Boolean
    suspend fun getByTrackingCode(code: String): AppointmentDto?
}