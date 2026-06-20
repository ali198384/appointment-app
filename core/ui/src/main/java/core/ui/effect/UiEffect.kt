package core.ui.effect

import core.ui.text.UiText

sealed class UiEffect {
    data class ShowSnackBar(val uiText: UiText) : UiEffect()
    data class NavigateById(val id: Long) : UiEffect()
    data class NavigateByString(val value: String) : UiEffect()
    data object Navigate : UiEffect()
    data object NavigateUp : UiEffect()
}