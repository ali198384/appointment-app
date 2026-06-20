package feature.profile.presentation.mvi

import android.net.Uri
import core.common.mvi.MviState
import core.ui.model.Gender
import core.ui.model.StandardTextFieldState

data class ProfileState(
    val name: StandardTextFieldState = StandardTextFieldState(),
    val nationalCode: StandardTextFieldState = StandardTextFieldState(),
    val mobileNumber: StandardTextFieldState = StandardTextFieldState(),
    val age: StandardTextFieldState = StandardTextFieldState(),
    val gender: Gender = Gender.MALE,
    val imageUri: Uri? = null,
    val loading: Boolean = false
): MviState
