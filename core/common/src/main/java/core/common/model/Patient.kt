package core.common.model

data class Patient(
    val name: String,
    val nationalCode: String,
    val mobileNumber: String,
    val age: Int,
    val isMale: Boolean
)
