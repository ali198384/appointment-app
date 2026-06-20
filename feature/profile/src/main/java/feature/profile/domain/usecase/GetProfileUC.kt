package feature.profile.domain.usecase

import core.common.model.Profile
import feature.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileUC @Inject constructor(
    private val repo: ProfileRepository
) {
    suspend operator fun invoke(): Profile? = repo.getProfile()
}