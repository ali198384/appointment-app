package feature.doctor.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import core.ui.R
import core.ui.component.AppToolbar
import core.ui.component.SearchablePagingListScreen
import core.ui.effect.CollectEffect
import core.ui.effect.UiEffect
import core.ui.text.UiText
import feature.doctor.presentation.component.DoctorItem
import feature.doctor.presentation.mvi.DoctorIntent


@Composable
fun DoctorScreen(
    onDoctorClick: (Long) -> Unit,
    onBack: () -> Unit,
    vm: DoctorViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()
    val pagingItems = state.doctors.collectAsLazyPagingItems()

    CollectEffect(vm.effect) { effect ->
        if (effect is UiEffect.NavigateById)
            onDoctorClick(effect.id)
    }

    Scaffold(
        topBar = {
            AppToolbar(
                titleRes = R.string.str_select_doctor,
                onBackClick = onBack
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            SearchablePagingListScreen(
                query = state.query,
                onQueryChange = {
                    vm.sendIntent(DoctorIntent.QueryChanged(it))
                },
                pagingItems = pagingItems,
                loading = state.loading,
                label = UiText.StringResource(R.string.str_doctor_search)
            ) { doctor ->

                DoctorItem(
                    item = doctor,
                    onClick = {
                        vm.sendIntent(
                            DoctorIntent.OnDoctorClick(doctor)
                        )
                    }
                )
            }
        }
    }
}
