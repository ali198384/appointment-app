package com.example.appointment.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import core.ui.R
import core.ui.component.AppSpacerHeight
import core.ui.theme.AppointmentTheme


@Composable
fun HomeButtonItem(
    @DrawableRes imageRes: Int,
    @StringRes textRes: Int,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .size(dimensionResource(com.intuit.sdp.R.dimen._76sdp))
            .shadow(
                dimensionResource(com.intuit.sdp.R.dimen._4sdp),
                MaterialTheme.shapes.extraLarge
            )
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.primary)
            .clickable { onClick() }
            .padding(dimensionResource(com.intuit.sdp.R.dimen._6sdp))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            painter = painterResource(imageRes),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )

        AppSpacerHeight(com.intuit.sdp.R.dimen._6sdp)

        Text(
            text = stringResource(textRes),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
@Preview
private fun HomeButtonItemPreview() {
    AppointmentTheme(darkTheme = true) {
        CompositionLocalProvider(LocalLayoutDirection provides (LayoutDirection.Rtl)) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        contentScale = ContentScale.FillWidth,
                        painter = painterResource(core.ui.R.drawable.logo_blue),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.large)
                            .border(
                                width = dimensionResource(com.intuit.sdp.R.dimen._1sdp),
                                color = MaterialTheme.colorScheme.outline,
                                shape = MaterialTheme.shapes.large
                            )
                    )

                    Spacer(Modifier.weight(1f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        HomeButtonItem(
                            imageRes = R.drawable.rounded_person_48,
                            textRes = R.string.str_profile
                        ) {}
                        HomeButtonItem(
                            imageRes = R.drawable.rounded_eye_tracking_48,
                            textRes = R.string.str_tracking_appointment
                        ) {}
                        HomeButtonItem(
                            imageRes = R.drawable.rounded_person_raised_hand_48,
                            textRes = R.string.str_get_appointment
                        ) {}
                    }
                }
            }
        }
    }
}