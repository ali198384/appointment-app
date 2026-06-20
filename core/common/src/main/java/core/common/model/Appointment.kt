package core.common.model

import java.time.LocalDateTime

data class Appointment (
    val timeSlotId: Long,
    val patientName: String,
    val trackingCode: String,
    val dateTime: LocalDateTime,
    val doctorName: String,
    val doctorImage: String?,
    val specialtyTitles: List<String>
)