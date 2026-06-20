package core.network.dto

import java.time.LocalDateTime

data class TimeSlotDto(
    val id: Long,
    val doctorId: Long,
    val dateTime: LocalDateTime,
    val reserved: Boolean
)
