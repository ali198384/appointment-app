package feature.appointment.presentation.create

import androidx.compose.material3.R
import androidx.lifecycle.SavedStateHandle
import core.common.model.Patient
import core.ui.base.BaseViewModel
import core.ui.effect.UiEffect
import core.ui.model.AddEditPatientResult
import core.ui.model.Gender
import core.ui.model.StandardTextFieldState
import core.ui.text.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import feature.appointment.domain.usecase.AddEditPatientValidationUC
import feature.appointment.domain.usecase.CreateAppointmentUC
import feature.appointment.domain.usecase.GetDoctorByRelationsUC
import feature.appointment.domain.usecase.GetProfileUC
import feature.appointment.domain.usecase.GetTimeSlotsUC
import feature.appointment.presentation.create.mvi.CreateAppointmentIntent
import feature.appointment.presentation.create.mvi.CreateAppointmentState
import feature.appointment.presentation.create.mvi.PatientType
import javax.inject.Inject

@HiltViewModel
class CreateAppointmentViewModel @Inject constructor(
    private val getTimeSlotUC: GetTimeSlotsUC,
    private val getDoctorByRelationsUC: GetDoctorByRelationsUC,
    private val getProfileUC: GetProfileUC,
    private val createAppointment: CreateAppointmentUC,
    private val addEditPatientValidationUC: AddEditPatientValidationUC,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CreateAppointmentIntent, CreateAppointmentState>() {

    override fun createInitialState() = CreateAppointmentState()

    init {
        savedStateHandle.get<Long>("doctorId")?.let {
            getTimeSlot(it)
        }
        getProfile()
    }

    private fun getProfile() {
        launchSafe(false) {
            val profile = getProfileUC.invoke()
            if (profile != null)
                setState { copy(profile = profile) }
        }
    }

    private fun getTimeSlot(id: Long) {
        launchSafe {
            val timeSlots = getTimeSlotUC.invoke(id)

            val doctor = getDoctorByRelationsUC.invoke(id)
            setState {
                copy(
                    doctor = doctor,
                    daySlotList = timeSlots
                )
            }
        }
    }

    override fun handleIntent(intent: CreateAppointmentIntent) {

        when (intent) {

            is CreateAppointmentIntent.SelectPatientType -> {
                setState {
                    copy(
                        patientType = intent.type,
                        showPatientSheet = intent.type == PatientType.OTHER
                    )
                }
            }

            is CreateAppointmentIntent.SelectTimeSlot -> {
                setState { copy(selectedTimeSlotId = intent.timeSlotId) }
            }

            is CreateAppointmentIntent.SelectDaySlot -> {
                setState { copy(selectedDayIndex = intent.daySlotIndex) }
            }

            is CreateAppointmentIntent.NameChanged -> setState {
                copy(nameOther = StandardTextFieldState(intent.value))
            }

            is CreateAppointmentIntent.NationalChanged -> setState {
                copy(nationalCodeOther = StandardTextFieldState(intent.value))
            }

            is CreateAppointmentIntent.MobileNumberChanged -> setState {
                copy(mobileNumberOther = StandardTextFieldState(intent.value))
            }

            is CreateAppointmentIntent.AgeChanged -> setState {
                copy(ageOther = StandardTextFieldState(intent.value))
            }

            is CreateAppointmentIntent.GenderChanged -> setState {
                copy(genderOther = intent.value)
            }

            is CreateAppointmentIntent.SaveNewPatient -> {
                val addEditProfileResult = addEditPatientValidationUC.invoke(
                    name = currentState.nameOther.text,
                    nationalCode = currentState.nationalCodeOther.text,
                    mobileNo = currentState.mobileNumberOther.text,
                    age = currentState.ageOther.text
                )

                handleValidationError(addEditProfileResult)

                if (!addEditProfileResult.success) return

                setState {
                    copy(
                        showPatientSheet = false,
                        patient = Patient(
                            nameOther.text,
                            nationalCodeOther.text,
                            mobileNumberOther.text,
                            ageOther.text.toInt(),
                            Gender.toBoolean(genderOther)
                        )
                    )
                }
            }

            CreateAppointmentIntent.DismissSheet -> {
                setState {
                    copy(
                        showPatientSheet = false,
                        patientType = PatientType.SELF,
                        nameOther = nameOther.copy(error= null),
                        nationalCodeOther = nationalCodeOther.copy(error= null),
                        mobileNumberOther = mobileNumberOther.copy(error= null),
                        ageOther = ageOther.copy(error= null)
                    )
                }
            }

            CreateAppointmentIntent.Submit -> submit()
        }
    }

    private fun handleValidationError(result: AddEditPatientResult) {
        val firstNameError = result.nameError
        if (firstNameError != null) {
            setState {
                copy(nameOther = currentState.nameOther.copy(error = firstNameError))
            }
        }

        val nationalCodeError = result.nationalCodeError
        if (nationalCodeError != null) {
            setState {
                copy(nationalCodeOther = currentState.nationalCodeOther.copy(error = nationalCodeError))
            }
        }

        val mobileNumError = result.mobileNoError
        if (mobileNumError != null) {
            setState {
                copy(mobileNumberOther = currentState.mobileNumberOther.copy(error = mobileNumError))
            }
        }

        val ageError = result.ageError
        if (ageError != null) {
            setState {
                copy(ageOther = currentState.ageOther.copy(error = ageError))
            }
        }
    }

    private fun submit() = launchSafe {

        val timeSlotId = state.value.selectedTimeSlotId

        if (timeSlotId == null) {
            sendEffect {
                UiEffect.ShowSnackBar(UiText.StringResource(core.ui.R.string.str_not_select_time_error))
            }
            return@launchSafe
        }

        val finalProfile = currentState.profile
        val finalPatient = if (currentState.patientType == PatientType.SELF && finalProfile != null)
            Patient(
                finalProfile.name,
                finalProfile.nationalCode,
                finalProfile.mobileNumber,
                finalProfile.age,
                finalProfile.isMale
            )
        else
            currentState.patient

        if (finalPatient != null) {
            val trackingCode = createAppointment.invoke(timeSlotId, finalPatient)
            sendEffect {
                UiEffect.NavigateByString(trackingCode)
            }
        } else {
            sendEffect {
                UiEffect.ShowSnackBar(UiText.StringResource(core.ui.R.string.str_patient_info_error))
            }
        }
    }

    override fun setLoading(value: Boolean) {
        setState { copy(loading = value) }
    }
}
