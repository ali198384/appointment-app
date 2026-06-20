package feature.profile.presentation

import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import core.sync.file.FileManager
import core.ui.R
import core.ui.base.BaseViewModel
import core.ui.effect.UiEffect
import core.ui.model.StandardTextFieldState
import core.ui.text.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import core.ui.model.AddEditPatientResult
import core.common.model.Profile
import core.ui.model.Gender
import core.ui.model.Gender.Companion.convertToGender
import feature.profile.domain.usecase.AddEditProfileValidationUC
import feature.profile.domain.usecase.GetProfileUC
import feature.profile.domain.usecase.SaveProfileUC
import feature.profile.presentation.mvi.ProfileIntent
import feature.profile.presentation.mvi.ProfileState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUC: GetProfileUC,
    private val saveProfileUC: SaveProfileUC,
    private val addEditProfileValidationUC: AddEditProfileValidationUC,
    private val fileManager: FileManager,
) : BaseViewModel<ProfileIntent, ProfileState>() {

    init {
        load()
    }

    override fun createInitialState(): ProfileState = ProfileState()

    override fun handleIntent(intent: ProfileIntent) {
        when (intent) {

            is ProfileIntent.Load -> load()

            is ProfileIntent.NameChanged -> setState {
                copy(name = StandardTextFieldState(intent.value))
            }

            is ProfileIntent.NationalChanged -> setState {
                copy(nationalCode = StandardTextFieldState(intent.value))
            }

            is ProfileIntent.MobileNumberChanged -> setState {
                copy(mobileNumber = StandardTextFieldState(intent.value))
            }

            is ProfileIntent.AgeChanged -> setState {
                copy(age = StandardTextFieldState(intent.value))
            }

            is ProfileIntent.GenderChanged -> setState {
                copy(gender = intent.value)
            }

            is ProfileIntent.ImagePicked -> setState {
                copy(imageUri = intent.uri)
            }

            ProfileIntent.Save -> save()
        }
    }

    private fun load() = viewModelScope.launch {
        getProfileUC()?.let {
            setState {
                copy(
                    name = StandardTextFieldState(it.name),
                    nationalCode = StandardTextFieldState(it.nationalCode),
                    mobileNumber = StandardTextFieldState(it.mobileNumber),
                    age = StandardTextFieldState(it.age.toString()),
                    imageUri = it.imagePath?.toUri(),
                    gender = convertToGender(it.isMale)

                )
            }
        }
    }

    private fun save() = launchSafe (
        onError = {
            UiEffect.ShowSnackBar(UiText.Dynamic(it.cause.toString()))
        }
    ) {
        val addEditProfileResult = addEditProfileValidationUC.invoke(
            name = currentState.name.text,
            nationalCode = currentState.nationalCode.text,
            mobileNo = currentState.mobileNumber.text,
            age = currentState.age.text
        )

        handleValidationError(addEditProfileResult)

        if (!addEditProfileResult.success) return@launchSafe

        val uri = currentState.imageUri

        val path = uri?.let {
            fileManager.copyToInternal(it)
        }

        saveProfileUC.invoke(
            Profile(
                name = currentState.name.text,
                nationalCode = currentState.nationalCode.text,
                mobileNumber = currentState.mobileNumber.text,
                age = currentState.age.text.toInt(),
                imagePath = path,
                isMale = Gender.toBoolean(currentState.gender)
            )
        )

        sendEffect {
            UiEffect.ShowSnackBar(UiText.StringResource(R.string.str_save_success))
            UiEffect.NavigateUp
        }
    }

    private fun handleValidationError(result: AddEditPatientResult) {
        val firstNameError = result.nameError
        if (firstNameError != null) {
            setState {
                copy(name = currentState.name.copy(error = firstNameError))
            }
        }

        val nationalCodeError = result.nationalCodeError
        if (nationalCodeError != null) {
            setState {
                copy(nationalCode = currentState.nationalCode.copy(error = nationalCodeError))
            }
        }

        val mobileNumError = result.mobileNoError
        if (mobileNumError != null) {
            setState {
                copy(mobileNumber = currentState.mobileNumber.copy(error = mobileNumError))
            }
        }

        val ageError = result.ageError
        if (ageError != null) {
            setState {
                copy(age = currentState.age.copy(error = ageError))
            }
        }
    }

    override fun setLoading(value: Boolean) {
        setState {
            copy(loading = value)
        }
    }
}
