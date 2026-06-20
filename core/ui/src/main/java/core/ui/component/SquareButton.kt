package core.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import core.ui.theme.AppointmentTheme

@Composable
fun SquareButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector = Icons.Rounded.Search,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    tintIcon: Color = MaterialTheme.colorScheme.surfaceTint,
    onClick: () -> Unit
) {

    Card(
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(
            dimensionResource(id = com.intuit.sdp.R.dimen._1sdp), borderColor
        ),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        onClick = { onClick.invoke() },
        modifier = modifier.size(56.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(dimensionResource(id = com.intuit.sdp.R.dimen._24sdp))
                    .align(Alignment.Center),
                imageVector = imageVector,
                contentDescription = "search",
                tint = tintIcon
            )
        }
    }
}

@Preview
@Composable
private fun FiltersButtonPreview() {
    AppointmentTheme() {
        CompositionLocalProvider(
            LocalLayoutDirection provides (LayoutDirection.Rtl)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                SquareButton {}
            }
        }
    }
}
