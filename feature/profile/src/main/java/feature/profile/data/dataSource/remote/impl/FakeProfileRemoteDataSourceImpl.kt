package feature.profile.data.dataSource.remote.impl

import core.network.dto.ProfileDto
import feature.profile.data.dataSource.remote.ProfileRemoteDataSource
import kotlinx.coroutines.delay
import java.io.File
import javax.inject.Inject

class FakeProfileRemoteDataSourceImpl @Inject constructor() : ProfileRemoteDataSource {

    override suspend fun updateProfile(profile: ProfileDto): Boolean {
        delay(800)
        // nothing (simulate success)
        return true
    }

    override suspend fun uploadImage(path: String): String {
        delay(800) // simulate network

        // fake url
        return "https://fake.server.com/images/${File(path).name}"
    }

    override suspend fun deleteImage(url: String) {
        delay(400) // simulate delete
    }
}
