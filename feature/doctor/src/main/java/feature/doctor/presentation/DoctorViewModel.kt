package feature.doctor.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import core.ui.base.BaseViewModel
import core.ui.effect.UiEffect
import core.ui.text.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import feature.doctor.domain.usecase.ObserveDoctorsUC
import feature.doctor.presentation.mvi.DoctorIntent
import feature.doctor.presentation.mvi.DoctorState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(
    private val observeDoctors: ObserveDoctorsUC,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<DoctorIntent, DoctorState>() {

    private val specialtyId = savedStateHandle.get<Long>("specialtyId")!!

    override fun createInitialState(): DoctorState = DoctorState()

    init {
        observeDoctors()
    }

    override fun handleIntent(intent: DoctorIntent) {
        when (intent) {
            is DoctorIntent.QueryChanged -> setState { copy(query = intent.query) }
            is DoctorIntent.OnDoctorClick -> {
                viewModelScope.launch {
                    sendEffect {
                        UiEffect.NavigateById(intent.doctor.id)
                    }
                }
            }
        }
    }

    // ---------------- OBSERVE ROOM FLOW ----------------

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeDoctors() {
        state
            .map { it.query }
            .debounce(400)
            .distinctUntilChanged()
            .mapLatest { query ->
                observeDoctors(specialtyId, query)
                    .cachedIn(viewModelScope)
            }
            .onEach { pagingFlow ->
                setState { copy(doctors = pagingFlow) }
            }
            .catch { e ->
                sendEffect {
                    UiEffect.ShowSnackBar(
                        UiText.Dynamic(e.message ?: "خطا در بارگذاری پزشکان")
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    override fun setLoading(value: Boolean) {
        setState {
            copy(loading = value)
        }
    }
}
