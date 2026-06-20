package feature.appointment.presentation.success

import androidx.lifecycle.SavedStateHandle
import core.ui.base.BaseViewModel
import core.ui.effect.UiEffect
import core.ui.text.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import feature.appointment.presentation.success.mvi.SuccessAppointmentIntent
import feature.appointment.presentation.success.mvi.SuccessAppointmentState
import javax.inject.Inject


@HiltViewModel
class SuccessAppointmentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SuccessAppointmentIntent, SuccessAppointmentState>() {

    override fun createInitialState() = SuccessAppointmentState()

    init {
        savedStateHandle.get<String>("trackingCode")?.let {
                setState { copy(trackingCode = it) }
        }
    }

    override fun handleIntent(intent: SuccessAppointmentIntent) {
        when (intent) {

            SuccessAppointmentIntent.CopyTrackingCode -> copy()

            SuccessAppointmentIntent.DoneClicked ->
                sendEffect { UiEffect.Navigate }
        }
    }

    private fun copy() {

        sendEffect {
            UiEffect.ShowSnackBar(UiText.Dynamic("کد رهگیری کپی شد"))
        }
    }
}
