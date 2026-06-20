package feature.appointment.presentation.create.mvi

import core.common.model.Doctor
import core.common.model.Patient
import core.common.model.Profile
import core.common.mvi.MviState
import core.ui.datetime.DaySlotsUi
import core.ui.model.Gender
import core.ui.model.StandardTextFieldState

data class CreateAppointmentState(
    val doctor: Doctor? = null,
    val patient: Patient? = null,
    val profile: Profile? = null,
    val patientType: PatientType = PatientType.SELF,
    val selectedDayIndex: Int = 0,
    val selectedTimeSlotId: Long? = null,
    val nameOther: StandardTextFieldState = StandardTextFieldState(),
    val nationalCodeOther: StandardTextFieldState = StandardTextFieldState(),
    val mobileNumberOther: StandardTextFieldState = StandardTextFieldState(),
    val ageOther: StandardTextFieldState = StandardTextFieldState(),
    val genderOther: Gender = Gender.MALE,
    val daySlotList: List<DaySlotsUi> = emptyList(),
    val showPatientSheet: Boolean = false,
    val loading: Boolean = false
): MviState

