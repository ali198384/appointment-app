package core.ui.error

import core.ui.R
import core.ui.text.UiText

sealed class TextFieldError(
    override val message: UiText
) : Error(message) {

    data object FieldEmpty :
        TextFieldError(UiText.StringResource(R.string.str_fill_this_text_box))

    data object InvalidMobileNumber :
        TextFieldError(UiText.StringResource(R.string.str_invalid_mobile_number))

    data object Invalid :
        TextFieldError(UiText.StringResource(R.string.str_invalid))
}
