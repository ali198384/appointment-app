package feature.specialty.presentation.mvi

import core.common.mvi.MviState
import core.common.model.Specialty

data class SpecialtyState(
    val loading: Boolean = false,
    val query: String = "",
    val items: List<Specialty> = emptyList(),
    val error: String? = null
) : MviState
