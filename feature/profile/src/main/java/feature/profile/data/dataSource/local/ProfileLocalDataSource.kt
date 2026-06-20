package feature.profile.data.dataSource.local

import core.database.local.entity.ProfileEntity

interface ProfileLocalDataSource {
    suspend fun getProfile(): ProfileEntity?
    suspend fun saveProfile(entity: ProfileEntity)
}
