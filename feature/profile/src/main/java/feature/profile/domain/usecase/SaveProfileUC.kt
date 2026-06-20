package feature.profile.domain.usecase

import core.common.model.Profile
import feature.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class SaveProfileUC @Inject constructor(
    private val repo: ProfileRepository
) {
    suspend operator fun invoke(profile: Profile) =
        repo.saveProfile(profile)
}