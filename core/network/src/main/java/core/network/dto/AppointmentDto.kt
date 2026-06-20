package core.network.dto

data class AppointmentDto(
    val timeSlotId: Long,
    val patientName: String,
    val patientNationalCode: String,
    val patientMobileNumber: String,
    val patientAge: Int,
    val patientIsMale: Boolean,
    val trackingCode: String? = null,
    val isDeleted: Boolean = false
)
