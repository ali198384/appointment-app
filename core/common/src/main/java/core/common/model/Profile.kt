package core.common.model

data class Profile(
    val id: Long = 1,
    val name: String,
    val nationalCode: String,
    val mobileNumber: String,
    val age: Int,
    val imagePath: String?,
    val isMale: Boolean
)