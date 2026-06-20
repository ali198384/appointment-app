package feature.appointment.data.dataSource.remote.impl

import core.network.api.AppointmentApi
import core.network.dto.AppointmentDto
import feature.appointment.data.dataSource.remote.AppointmentRemoteDataSource
import javax.inject.Inject

class AppointmentRemoteDataSourceImpl @Inject constructor(
    private val api: AppointmentApi
) : AppointmentRemoteDataSource {



    override suspend fun getTimeSlots(doctorId: Long) =
        api.getSlots(doctorId)

    override suspend fun createAppointment(body: AppointmentDto): String? =
        api.createAppointment(body).body()

    override suspend fun deleteAppointment(trackingCode: String): Boolean =
        try {
            api.deleteAppointment(trackingCode).isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    override suspend fun getByTrackingCode(code: String): AppointmentDto =
        api.getAppointmentByTrackingCode(code)
}
