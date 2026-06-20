package feature.profile.data.repository

import android.util.Log
import core.database.model.SyncState
import core.sync.file.FileManager
import core.sync.manager.SyncManager
import feature.profile.data.dataSource.local.ProfileLocalDataSource
import feature.profile.data.dataSource.remote.ProfileRemoteDataSource
import feature.profile.data.mapper.toDomain
import feature.profile.data.mapper.toDto
import feature.profile.data.mapper.toEntity
import core.common.model.Profile
import feature.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val local: ProfileLocalDataSource,
    private val remote: ProfileRemoteDataSource,
    private val fileManager: FileManager,
    private val syncManager: SyncManager
) : ProfileRepository {

    override suspend fun getProfile(): Profile? {
        return local.getProfile()?.toDomain()
    }

    override suspend fun saveProfile(profile: Profile) {
        val old = local.getProfile()

        val oldPath = old?.imagePath
        val newPath = profile.imagePath

        if (oldPath != null && oldPath != newPath) {
            fileManager.deleteImage(oldPath)
        }

        local.saveProfile(
            profile.toEntity()
        )

        syncManager.triggerNow()
    }

    override suspend fun syncIfNeeded() {
        val entity = local.getProfile() ?: return
        if (entity.syncState != SyncState.SYNCED) return

        // =========================
        // اگر عکس local داریم → upload
        // =========================
        var newUrl = entity.imageUrl

        // 1️⃣ اگر عکس جدید داری → آپلود
        entity.imagePath?.let { path ->
            newUrl = remote.uploadImage(path)
        }

        // =========================
        // آپدیت پروفایل سمت سرور
        // =========================
        remote.updateProfile(
            entity.toDto(newUrl)
        )

        // =========================
        // پاک کردن عکس قبلی remote
        // =========================
        if (entity.imageUrl != null &&
            entity.imageUrl != newUrl
        ) {
            runCatching {
                remote.deleteImage(entity.imageUrl!!)
            }
        }

        // =========================
        // پاک کردن عکس قبلی local
        // =========================
        // در صورت داشتن سرور
        /*entity.imagePath?.let {
            runCatching {
                fileManager.deleteImage(it)
            }
        }*/

        // =========================
        // ذخیره نهایی
        // =========================
        local.saveProfile(
            entity.copy(
                //imageUrl = newUrl, // در صورت داشتن سرور
                //imagePath = null, // در صورت داشتن سرور
                syncState = SyncState.SYNCED
            )
        )
    }

    fun myFunc(path: String) {
        runCatching {
            fileManager.deleteImage(path)
        }
        Log.d("TAG", "anotherState")
    }
}
