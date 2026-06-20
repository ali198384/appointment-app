package feature.profile.data.dataSource.remote.impl

import core.network.api.ProfileApi
import core.network.dto.ProfileDto
import feature.profile.data.dataSource.remote.ProfileRemoteDataSource
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.net.URLConnection
import javax.inject.Inject

class ProfileRemoteDataSourceImpl @Inject constructor(
    private val api: ProfileApi
): ProfileRemoteDataSource {

    override suspend fun updateProfile(profile: ProfileDto): Boolean =
        try {
            api.updateProfile(profile).isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    override suspend fun uploadImage(path: String): String {

        val file = File(path)

        require(file.exists()) {
            "Image file not found: $path"
        }

        val mime = URLConnection.guessContentTypeFromName(file.name)
            ?: "image/jpeg"


        val requestBody = file
            .asRequestBody(mime.toMediaType())

        val part = MultipartBody.Part.createFormData(
            name = "image",
            filename = file.name,
            body = requestBody
        )

        val response = api.uploadImage(part)

        if (response.isSuccessful) {
            return response.body()
                ?: error("Empty body")
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun deleteImage(url: String) {
        api.deleteImage(url)
    }
}
