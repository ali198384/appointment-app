package feature.appointment.presentation.find.mvi

import core.common.mvi.MviIntent


sealed interface FindAppointmentIntent: MviIntent {
    data class CodeChanged(val value: String) : FindAppointmentIntent
    data class ShowStatusDeleteBottomSheet(val isShow: Boolean) : FindAppointmentIntent
    data object Search : FindAppointmentIntent
    data object DeleteAppointment : FindAppointmentIntent
}