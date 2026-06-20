package feature.appointment.presentation.find.mvi

import core.common.model.Appointment
import core.common.mvi.MviState
import core.ui.model.StandardTextFieldState


data class FindAppointmentState (
    val loading: Boolean = false,
    val showDeleteSheet: Boolean = false,
    val appointment: Appointment? = null,
    val userTrackingCode: StandardTextFieldState = StandardTextFieldState(),
): MviState