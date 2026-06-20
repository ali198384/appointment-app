package core.network.dto

data class DoctorDto(
    val id: Long,
    val name: String,
    val imageUrl: String?,
    val specialtyIds: List<Long>,
    val timeSlots: List<TimeSlotDto>
)
