package core.common.model

data class Doctor(
    val id: Long,
    val name: String,
    val imageUrl: String?,
    val specialtyTitles: List<String>,
    val nextAvailableTimeSlot: TimeSlot?
)
