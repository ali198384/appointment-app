package core.database.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import core.database.model.SyncState

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey
    val id: Long = 1,
    val name: String,
    val nationalCode: String,
    val phone: String,
    val age: Int,
    val isMale: Boolean,
    val imagePath: String?,
    val imageUrl: String?,  // url سرور (future)
    val syncState: SyncState = SyncState.PENDING_INSERT
)

