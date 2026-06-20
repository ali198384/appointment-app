package core.ui.component

import androidx.annotation.DimenRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource

@Composable
fun AppSpacerWidth(@DimenRes id: Int) {
    Spacer(modifier = Modifier.width(dimensionResource(id = id)))
}