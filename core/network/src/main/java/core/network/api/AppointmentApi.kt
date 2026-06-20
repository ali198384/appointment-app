package core.network.api

import core.network.dto.AppointmentDto
import core.network.dto.TimeSlotDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppointmentApi {

    @POST("appointments")
    suspend fun createAppointment(
        @Body request: AppointmentDto
    ): Response<String>

    @GET("appointments/{trackingCode}")
    suspend fun getAppointmentByTrackingCode(
        @Path("trackingCode") trackingCode: String
    ): AppointmentDto

    @DELETE("appointments/{trackingCode}")
    suspend fun deleteAppointment(
        @Path("trackingCode") trackingCode: String
    ): Response<Unit>

    @GET("doctors/{id}/slots")
    suspend fun getSlots(
        @Path("id") doctorId: Long
    ): List<TimeSlotDto>
}
