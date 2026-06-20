package core.database.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import core.database.local.entity.AppointmentEntity
import core.database.local.entity.TimeSlotEntity


data class AppointmentWithRelations(

    @Embedded
    val appointment: AppointmentEntity,

    @Relation(
        parentColumn = "timeSlotId",
        entityColumn = "id",
        entity = TimeSlotEntity::class
    )
    val timeSlot: TimeSlotWithDoctor
)