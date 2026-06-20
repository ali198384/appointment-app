package feature.appointment.presentation.find

import core.ui.R
import core.ui.base.BaseViewModel
import core.ui.effect.UiEffect
import core.ui.error.TextFieldError
import core.ui.model.StandardTextFieldState
import core.ui.text.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import feature.appointment.domain.usecase.DeleteAppointmentUC
import feature.appointment.domain.usecase.FindAppointmentByTrackingCodeUC
import feature.appointment.presentation.find.mvi.FindAppointmentIntent
import feature.appointment.presentation.find.mvi.FindAppointmentState
import javax.inject.Inject

@HiltViewModel
class FindAppointmentViewModel @Inject constructor(
    private val findAppointment: FindAppointmentByTrackingCodeUC,
    private val deleteAppointment: DeleteAppointmentUC
) : BaseViewModel<FindAppointmentIntent, FindAppointmentState>() {

    override fun createInitialState(): FindAppointmentState = FindAppointmentState()

    override fun handleIntent(intent: FindAppointmentIntent) {
        when (intent) {

            is FindAppointmentIntent.CodeChanged ->
                setState { copy(userTrackingCode = StandardTextFieldState(intent.value)) }

            is FindAppointmentIntent.Search -> search()

            is FindAppointmentIntent.ShowStatusDeleteBottomSheet ->
                setState { copy(showDeleteSheet = intent.isShow) }

            is FindAppointmentIntent.DeleteAppointment -> delete()
        }
    }

    private fun search() {
        var searchError: TextFieldError? = null
        val searchText = state.value.userTrackingCode.text

        if (searchText.isBlank())
            searchError = TextFieldError.FieldEmpty
        else if (searchText.length < 10)
            searchError = TextFieldError.Invalid

        if (searchError != null) {
            setState {
                copy(
                    userTrackingCode = userTrackingCode.copy( error = searchError)
                )
            }
            return
        }

        launchSafe {
            val result = findAppointment(state.value.userTrackingCode.text)

            setState { copy(appointment = result) }

            if (result == null)
                sendEffect {
                    UiEffect.ShowSnackBar(UiText.StringResource(R.string.str_not_found_record))
                }
        }
    }

    private fun delete() = launchSafe {
        state.value.appointment?.let {
            deleteAppointment.invoke(it.trackingCode, it.timeSlotId)
            sendEffect { UiEffect.NavigateUp }
        }
    }

    override fun setLoading(value: Boolean) {
        setState { copy(loading = value) }
    }
}