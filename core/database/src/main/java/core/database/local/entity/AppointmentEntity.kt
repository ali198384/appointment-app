package core.database.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import core.database.model.SyncState


@Entity(
    tableName = "appointments",
    /*foreignKeys = [
        ForeignKey(
            entity = TimeSlotEntity::class,
            parentColumns = ["id"],
            childColumns = ["timeSlotId"],
            onDelete = CASCADE
        )
    ],
    indices = [
        Index("timeSlotId"),
        Index("trackingCode", unique = true)
    ]*/
)
data class AppointmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timeSlotId: Long,
    val patientName: String,
    val patientNationalCode: String,
    val patientMobileNumber: String,
    val patientAge: Int,
    val patientIsMale: Boolean,
    val trackingCode: String? = null,
    val isDeleted: Boolean = false,
    val syncState: SyncState = SyncState.PENDING_INSERT
)
