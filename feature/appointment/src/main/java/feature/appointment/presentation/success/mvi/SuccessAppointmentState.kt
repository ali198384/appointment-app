package feature.appointment.presentation.success.mvi

import core.common.mvi.MviState

data class SuccessAppointmentState (
    val trackingCode: String = ""
): MviState
