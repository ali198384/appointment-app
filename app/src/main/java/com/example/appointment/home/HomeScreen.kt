package com.example.appointment.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import core.ui.R
import core.ui.component.AppSpacerHeight
import core.ui.component.AppToolbar
import core.ui.component.ImageSlider
import core.ui.findActivity
import core.ui.openUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProfileClick: () -> Unit,
    onCurrentClick: () -> Unit,
    onGetAppointmentClick: () -> Unit
) {
    val activity = LocalContext.current.findActivity()
    BackHandler {
        activity?.finish()
    }
    Scaffold(
        topBar = {
            AppToolbar(
                titleRes = R.string.str_toolbar_title
            ) {
                activity?.finish()
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = dimensionResource(com.intuit.sdp.R.dimen._100sdp))
                    .navigationBarsPadding()
                    .padding(dimensionResource(com.intuit.sdp.R.dimen._12sdp))
                    .padding(bottom = dimensionResource(com.intuit.sdp.R.dimen._24sdp)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HomeButtonItem(
                    imageRes = R.drawable.rounded_person_48,
                    textRes = R.string.str_profile,
                    onClick = onProfileClick
                )
                HomeButtonItem(
                    imageRes = R.drawable.rounded_eye_tracking_48,
                    textRes = R.string.str_tracking_appointment,
                    onClick = onCurrentClick
                )
                HomeButtonItem(
                    imageRes = R.drawable.rounded_person_raised_hand_48,
                    textRes = R.string.str_get_appointment,
                    onClick = onGetAppointmentClick
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        HandleHomePage(pagePadding = padding)
    }
}

@Composable
fun HandleHomePage(pagePadding: PaddingValues) {
    Column(
        Modifier
            .padding(pagePadding)
            .fillMaxSize()
            .padding(dimensionResource(com.intuit.sdp.R.dimen._12sdp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppSpacerHeight(com.intuit.sdp.R.dimen._42sdp)

        Image(
            contentScale = ContentScale.FillWidth,
            painter = painterResource(
                if (isSystemInDarkTheme())
                    R.drawable.logo_white
                else
                    R.drawable.logo_blue
            ),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth()
        )

        AppSpacerHeight(com.intuit.sdp.R.dimen._36sdp)
        val context = LocalContext.current
        Card {
            ImageSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                images = listOf(
                    R.drawable.banner1,
                    R.drawable.banner2,
                    R.drawable.banner3,
                    R.drawable.banner4
                )
            ){
                context.openUrl("https://baq.bmsu.ac.ir/")
            }
        }
    }
}
