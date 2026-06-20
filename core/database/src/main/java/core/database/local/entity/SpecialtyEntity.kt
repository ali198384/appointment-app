package core.database.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "specialties")
data class SpecialtyEntity(
    @PrimaryKey val id: Long,
    val title: String
)
