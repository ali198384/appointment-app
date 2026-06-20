package feature.profile.data.dataSource.remote

import core.network.dto.ProfileDto

interface ProfileRemoteDataSource {
    suspend fun updateProfile(profile: ProfileDto): Boolean
    suspend fun uploadImage(path: String): String
    suspend fun deleteImage(url: String)
}
