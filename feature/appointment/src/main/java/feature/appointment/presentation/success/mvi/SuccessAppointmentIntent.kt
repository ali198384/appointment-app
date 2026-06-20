package feature.appointment.presentation.success.mvi

import core.common.mvi.MviIntent


sealed interface SuccessAppointmentIntent: MviIntent {

    data object CopyTrackingCode : SuccessAppointmentIntent
    data object DoneClicked : SuccessAppointmentIntent
}
