package core.network.api

import core.network.dto.DoctorDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DoctorApi {

    @GET("doctors")
    suspend fun getDoctors(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("specialtyId") specialtyId: Long,
        @Query("query") query: String?
    ): List<DoctorDto>
}
