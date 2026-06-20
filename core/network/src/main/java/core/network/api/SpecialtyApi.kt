package core.network.api

import core.network.dto.SpecialtyDto
import retrofit2.Response
import retrofit2.http.GET

interface SpecialtyApi {

    @GET("specialties")
    suspend fun getSpecialties(): List<SpecialtyDto>
}
