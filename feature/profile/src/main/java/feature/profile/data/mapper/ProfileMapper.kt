package feature.profile.data.mapper


import core.database.local.entity.ProfileEntity
import core.database.model.SyncState
import core.network.dto.ProfileDto
import core.common.model.Profile


fun ProfileEntity.toDto(newImageUrl: String? = imageUrl) =
    ProfileDto(
        id,
        name,
        nationalCode,
        phone,
        age,
        isMale,
        newImageUrl
    )

fun ProfileEntity.toDomain() =
    Profile(
        id,
        name,
        nationalCode,
        phone,
        age,
        imageUrl ?: imagePath,
        isMale
    )

fun Profile.toEntity(syncState: SyncState = SyncState.PENDING_INSERT) =
    ProfileEntity(
        id,
        name,
        nationalCode,
        mobileNumber,
        age,
        isMale,
        imagePath,
        null,
        syncState
    )
