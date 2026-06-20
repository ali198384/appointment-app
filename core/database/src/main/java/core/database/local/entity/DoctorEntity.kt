package core.database.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "doctors")
data class DoctorEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val imageUrl: String?
)
