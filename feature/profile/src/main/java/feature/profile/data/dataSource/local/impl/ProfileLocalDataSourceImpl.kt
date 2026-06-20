package feature.profile.data.dataSource.local.impl

import core.database.local.dao.ProfileDao
import core.database.local.entity.ProfileEntity
import feature.profile.data.dataSource.local.ProfileLocalDataSource
import javax.inject.Inject

class ProfileLocalDataSourceImpl @Inject constructor(
    private val dao: ProfileDao
): ProfileLocalDataSource {

    override suspend fun getProfile() = dao.getProfile()

    override suspend fun saveProfile(entity: ProfileEntity) =
        dao.saveProfile(entity)
}