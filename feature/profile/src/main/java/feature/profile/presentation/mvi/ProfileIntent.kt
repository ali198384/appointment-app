package feature.profile.presentation.mvi

import android.net.Uri
import core.common.mvi.MviIntent
import core.ui.model.Gender

sealed interface ProfileIntent: MviIntent {

    data object Load : ProfileIntent
    data class NameChanged(val value: String) : ProfileIntent
    data class NationalChanged(val value: String) : ProfileIntent
    data class MobileNumberChanged(val value: String) : ProfileIntent
    data class AgeChanged(val value: String) : ProfileIntent
    data class GenderChanged(val value: Gender) : ProfileIntent
    data class ImagePicked(val uri: Uri) : ProfileIntent
    data object Save : ProfileIntent
}
