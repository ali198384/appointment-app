package feature.appointment.presentation.create.mvi

import androidx.annotation.StringRes
import core.common.model.Patient
import core.common.mvi.MviIntent
import core.ui.model.Gender

sealed interface CreateAppointmentIntent: MviIntent {

    data class NameChanged(val value: String) : CreateAppointmentIntent
    
    data class NationalChanged(val value: String) : CreateAppointmentIntent
    
    data class MobileNumberChanged(val value: String) : CreateAppointmentIntent
    
    data class AgeChanged(val value: String) : CreateAppointmentIntent
    
    data class GenderChanged(val value: Gender) : CreateAppointmentIntent
    
    data class SelectPatientType(val type: PatientType) : CreateAppointmentIntent

    data class SelectTimeSlot(val timeSlotId: Long) : CreateAppointmentIntent

    data class SelectDaySlot(val daySlotIndex: Int) : CreateAppointmentIntent

    data object SaveNewPatient : CreateAppointmentIntent

    data object Submit : CreateAppointmentIntent

    data object DismissSheet : CreateAppointmentIntent
}

enum class PatientType (@param:StringRes val displayResId: Int) {
    SELF(core.ui.R.string.str_self),
    OTHER(core.ui.R.string.str_other)
}