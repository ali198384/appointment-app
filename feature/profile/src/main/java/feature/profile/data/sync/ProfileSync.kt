package feature.profile.data.sync

import core.sync.manager.Syncable
import feature.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileSync @Inject constructor(
    private val repo: ProfileRepository
) : Syncable {

    override suspend fun sync() {
        repo.syncIfNeeded()
    }
}
