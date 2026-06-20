package feature.profile.domain.repository

import core.common.model.Profile

interface ProfileRepository {
    suspend fun getProfile(): Profile?
    suspend fun saveProfile(profile: Profile)
    suspend fun syncIfNeeded()
}