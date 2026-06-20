package core.database.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import core.database.local.entity.DoctorEntity
import core.database.local.entity.TimeSlotEntity

data class TimeSlotWithDoctor(

    @Embedded
    val timeSlot: TimeSlotEntity,

    @Relation(
        entity = DoctorEntity::class,
        parentColumn = "doctorId",
        entityColumn = "id"
    )
    val doctor: DoctorWithSpecialties
)