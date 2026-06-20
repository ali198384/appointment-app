package feature.appointment.data.mapper

import core.common.model.TimeSlot
import core.database.local.entity.TimeSlotEntity
import core.network.dto.TimeSlotDto
import core.ui.datetime.toTimeText


fun TimeSlotEntity.toDomain() =
    TimeSlot(id, dateTime, dateTime.toTimeText())
fun TimeSlotDto.toDomain(doctorId: Long) =
    TimeSlot(
        id = id,
        dateTime = dateTime,
        dateTime.toTimeText()
    )

fun TimeSlotDto.toEntity() =
    TimeSlotEntity(
        id = id,
        doctorId = doctorId,
        dateTime = dateTime,
        reserved = reserved
    )