package core.network.api

import core.network.dto.ProfileDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface ProfileApi {
    @PUT("profile")
    suspend fun updateProfile(
        @Body dto: ProfileDto
    ): Response<Unit>

    @Multipart
    @POST("profile/image")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<String>

    @DELETE("profile/image")
    suspend fun deleteImage(
        @Query("url") imageUrl: String
    ): Response<Unit>
}
