package feature.specialty.presentation.mvi

import core.common.mvi.MviIntent

sealed interface SpecialtyIntent : MviIntent {
    data class QueryChanged(val value: String) : SpecialtyIntent
    data class ClickSpecialty(val id: Long) : SpecialtyIntent
}
