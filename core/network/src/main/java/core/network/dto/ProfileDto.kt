package core.network.dto

data class ProfileDto(
    val id: Long,
    val name: String,
    val nationalCode: String,
    val phone: String,
    val age: Int,
    val isMale: Boolean,
    val imageUrl: String?
)
