package core.database.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import core.database.local.entity.DoctorEntity
import core.database.local.entity.DoctorSpecialtyCrossRef
import core.database.local.entity.SpecialtyEntity

data class DoctorWithSpecialties (
    @Embedded
    val doctor: DoctorEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = DoctorSpecialtyCrossRef::class,
            parentColumn = "doctorId",
            entityColumn = "specialtyId"
        )
    )
    val specialties: List<SpecialtyEntity>
)