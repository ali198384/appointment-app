package core.ui.effect

import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> CollectEffect(
    effectFlow: Flow<T>,
    onEffect: suspend (T) -> Unit
) {
    LaunchedEffect(Unit) {
        effectFlow.collect {
            onEffect(it)
        }
    }
}
