package feature.appointment.presentation.create.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import core.ui.component.AppSpacerHeight
import core.ui.theme.AppointmentTheme

@Composable
fun DayItem(
    weekName: String,
    dayOfMonth: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (!selected)
                MaterialTheme.colorScheme.tertiary
            else
                MaterialTheme.colorScheme.primary,
            contentColor = if (!selected)
                MaterialTheme.colorScheme.onTertiary
            else
                MaterialTheme.colorScheme.onPrimary
        ),
        border = BorderStroke(
            width = dimensionResource(id = com.intuit.sdp.R.dimen._1sdp),
            color = if (!selected)
                MaterialTheme.colorScheme.tertiaryContainer
            else
                MaterialTheme.colorScheme.primary
        ),
        onClick = onClick
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(dimensionResource(com.intuit.sdp.R.dimen._12sdp))
        ) {
            Text(
                text = weekName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            AppSpacerHeight(com.intuit.sdp.R.dimen._12sdp)
            Text(
                text = dayOfMonth,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
@Preview
private fun DayItemPreview() {
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

                    DayItem(
                        "دوشنبه",
                        "18 اردیبهشت",
                        false,
                    ) {}
                    AppSpacerHeight(com.intuit.sdp.R.dimen._8sdp)
                    DayItem(
                        "دوشنبه",
                        "18 اردیبهشت",
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
                    DayItem(
                        "دوشنبه",
                        "18 اردیبهشت",
                        false,
                    ) {}
                    AppSpacerHeight(com.intuit.sdp.R.dimen._8sdp)
                    DayItem(
                        "دوشنبه",
                        "18 اردیبهشت",
                        true,
                    ) {}
                }
            }
        }
    }
}

