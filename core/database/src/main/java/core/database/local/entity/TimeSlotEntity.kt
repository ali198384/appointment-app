package core.database.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime


@Entity(tableName = "time_slots",)
data class TimeSlotEntity(
    @PrimaryKey val id: Long,
    val doctorId: Long,
    val dateTime: LocalDateTime,
    val reserved: Boolean
)
