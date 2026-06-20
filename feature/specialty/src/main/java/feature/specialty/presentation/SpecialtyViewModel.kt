package feature.specialty.presentation

import core.ui.base.BaseViewModel
import core.ui.effect.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import feature.specialty.domain.usecase.ObserveSpecialtiesUC
import feature.specialty.presentation.mvi.SpecialtyIntent
import feature.specialty.presentation.mvi.SpecialtyState
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SpecialtyViewModel @Inject constructor(
    private val observeSpecialties: ObserveSpecialtiesUC
) : BaseViewModel<SpecialtyIntent, SpecialtyState>() {

    override fun createInitialState(): SpecialtyState = SpecialtyState()

    init {
        observe()
    }

    // ---------------- INTENT HANDLER ----------------

    override fun handleIntent(intent: SpecialtyIntent) {
        when (intent) {
            is SpecialtyIntent.QueryChanged ->
                setState { copy(query = intent.value) }

            is SpecialtyIntent.ClickSpecialty ->
                sendEffect { UiEffect.NavigateById(intent.id) }
        }
    }

    // ---------------- OBSERVE ROOM FLOW ----------------

    private fun observe() {
        collectWithSearchAndLoad(
            sourceFlow = state.map { it.query },
            observeBlock = { query ->
                observeSpecialties.invoke(query)
            },
            onEachBlock = { list ->
                setState { copy(items = list) }
            }
        )
    }

    // ---------------- LOADING SUPPORT ----------------

    override fun setLoading(value: Boolean) {
        setState { copy(loading = value) }
    }
}

