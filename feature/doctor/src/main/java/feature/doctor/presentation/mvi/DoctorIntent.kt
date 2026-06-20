package feature.doctor.presentation.mvi

import core.common.model.Doctor
import core.common.mvi.MviIntent

sealed interface DoctorIntent : MviIntent {
    data class QueryChanged(val query: String) : DoctorIntent
    data class OnDoctorClick(val doctor: Doctor) : DoctorIntent
}
