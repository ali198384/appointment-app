package feature.doctor.presentation.mvi

import androidx.paging.PagingData
import core.common.model.Doctor
import core.common.mvi.MviState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class DoctorState(
    val loading: Boolean = false,
    val doctors: Flow<PagingData<Doctor>> = emptyFlow(),
    val query: String = ""
) : MviState
