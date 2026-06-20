package core.ui.model

import core.ui.error.TextFieldError

data class AddEditPatientResult(
    val nameError: TextFieldError? = null,
    val nationalCodeError: TextFieldError? = null,
    val mobileNoError: TextFieldError? = null,
    val ageError: TextFieldError? = null,
    val success: Boolean = false
)