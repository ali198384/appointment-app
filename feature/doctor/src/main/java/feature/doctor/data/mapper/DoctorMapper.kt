package feature.doctor.data.mapper

import core.common.model.Doctor
import core.common.model.TimeSlot
import core.database.local.entity.DoctorEntity
import core.database.local.entity.DoctorSpecialtyCrossRef
import core.database.local.relation.DoctorWithRelations
import core.network.dto.DoctorDto
import core.ui.datetime.toPersianDateTime
import core.ui.datetime.toTimeText

fun DoctorDto.toEntity() =
    DoctorEntity(id, name, imageUrl)

fun DoctorDto.toCrossRefs() =
    specialtyIds.map { DoctorSpecialtyCrossRef(id, it) }

fun DoctorWithRelations.toDomain(): Doctor {

    val nextSlot =
        timeSlots
            .filter { !it.reserved }
            .minByOrNull { it.dateTime }

    return Doctor(
        id = doctor.id,
        name = doctor.name,
        imageUrl = doctor.imageUrl,
        specialtyTitles = specialties.map { it.title },
        nextAvailableTimeSlot = nextSlot?.let {
            TimeSlot(it.id, it.dateTime, it.dateTime.toPersianDateTime())
        }
    )
}
