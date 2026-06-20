package feature.specialty.presentation

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
import core.ui.R
import core.ui.component.AppToolbar
import core.ui.component.SearchableListScreen
import core.ui.effect.CollectEffect
import core.ui.effect.UiEffect
import core.ui.text.UiText
import feature.specialty.presentation.component.SpecialtyItem
import feature.specialty.presentation.mvi.SpecialtyIntent

@Composable
fun SpecialtyScreen(
    onNavigateToDoctors: (Long) -> Unit,
    onBack: () -> Unit,
    vm: SpecialtyViewModel = hiltViewModel()
) {

    val state by vm.state.collectAsState()

    // -------- EFFECTS --------
    CollectEffect(vm.effect) { effect ->

        if (effect is UiEffect.NavigateById)
            onNavigateToDoctors(effect.id)
    }

    Scaffold(
        topBar = {
            AppToolbar(
                titleRes = R.string.str_select_specialty,
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
            SearchableListScreen(
                query = state.query,
                onQueryChange = {
                    vm.sendIntent(SpecialtyIntent.QueryChanged(it))
                },
                items = state.items,
                loading = state.loading,
                label = UiText.StringResource(R.string.str_specialty_search),
                key = { it.id }
            ) { item ->

                SpecialtyItem(
                    item = item,
                    onClick = {
                        vm.sendIntent(
                            SpecialtyIntent.ClickSpecialty(item.id)
                        )
                    }
                )
            }
        }
    }
}
