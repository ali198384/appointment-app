package feature.appointment.presentation.create.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import core.ui.R
import core.ui.component.AppSpacerHeight
import core.ui.theme.AppointmentTheme

@Composable
fun TimeSlotChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.onSurface,
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
        ),
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                modifier = Modifier
                    .padding(vertical = dimensionResource(com.intuit.sdp.R.dimen._4sdp))
                    .fillMaxWidth(),
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    )
}

@Composable
@Preview
private fun TimeSlotChipPreview() {
    CompositionLocalProvider(LocalLayoutDirection provides (LayoutDirection.Rtl)) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppointmentTheme(darkTheme = false) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(dimensionResource(com.intuit.sdp.R.dimen._8sdp)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    TimeSlotChip(
                        "18:20",
                        false,
                    ) {}
                    AppSpacerHeight(com.intuit.sdp.R.dimen._8sdp)
                    TimeSlotChip(
                        "18:20",
                        true,
                    ) {}
                }
            }
            AppointmentTheme(darkTheme = true) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(dimensionResource(com.intuit.sdp.R.dimen._8sdp)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TimeSlotChip(
                        "18:20",
                        false,
                    ) {}
                    AppSpacerHeight(com.intuit.sdp.R.dimen._8sdp)
                    TimeSlotChip(
                        "18:20",
                        true,
                    ) {}
                }
            }
        }
    }
}
