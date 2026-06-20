package core.ui.model

import core.ui.error.Error

data class StandardTextFieldState (
    var text: String = "",
    val error: Error? = null
)