package core.database.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index

@Entity(
    tableName = "doctor_specialty_cross_ref",
    primaryKeys = ["doctorId", "specialtyId"],
    foreignKeys = [
        ForeignKey(
            entity = DoctorEntity::class,
            parentColumns = ["id"],
            childColumns = ["doctorId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = SpecialtyEntity::class,
            parentColumns = ["id"],
            childColumns = ["specialtyId"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("doctorId"), Index("specialtyId")]
)
data class DoctorSpecialtyCrossRef(
    val doctorId: Long,
    val specialtyId: Long
)
