package core.common.model


import java.time.LocalDateTime

data class TimeSlot(
    val id: Long,
    val dateTime: LocalDateTime,
    val timeText: String
)
