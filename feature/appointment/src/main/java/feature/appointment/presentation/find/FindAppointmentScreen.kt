package feature.appointment.presentation.find

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import core.common.model.Appointment
import core.ui.R
import core.ui.component.AppSpacerHeight
import core.ui.component.AppSpacerWidth
import core.ui.component.AppTextField
import core.ui.component.AppToolbar
import core.ui.component.AppTwoButton
import core.ui.component.SquareButton
import core.ui.effect.UiEffect
import core.ui.model.StandardTextFieldState
import core.ui.text.UiText
import core.ui.view.LoadingView
import feature.appointment.presentation.find.component.AppointmentCard
import feature.appointment.presentation.find.mvi.FindAppointmentIntent


@Composable
fun FindAppointmentScreen(
    vm: FindAppointmentViewModel = hiltViewModel(),
    onBackHome: () -> Unit
) {

    val context = LocalContext.current
    val state by vm.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        vm.effect.collect { effect ->
            when (effect) {
                is UiEffect.ShowSnackBar ->
                    snackBarHostState.showSnackbar(effect.uiText.asString(context))

                is UiEffect.NavigateUp -> onBackHome()

                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            AppToolbar(
                titleRes = R.string.str_tracking_appointment,
                onBackClick = onBackHome
            )
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Box(Modifier.padding(padding)) {

            PageContent(
                userTrackingCode = state.userTrackingCode,
                appointment = state.appointment,
            ) { onIntent ->
                vm.sendIntent(onIntent)
            }

            if (state.loading) {
                LoadingView()
            }

            if (state.showDeleteSheet) {
                ConfirmDeleteBottomSheet { isAccept ->
                    vm.sendIntent(
                        if (isAccept)
                            FindAppointmentIntent.DeleteAppointment
                        else
                            FindAppointmentIntent.ShowStatusDeleteBottomSheet(false)
                    )

                }
            }
        }
    }
}

@Composable
private fun PageContent(
    userTrackingCode: StandardTextFieldState,
    appointment: Appointment?,
    onIntent: (FindAppointmentIntent) -> Unit
) {
    Column(Modifier.padding(horizontal = dimensionResource(com.intuit.sdp.R.dimen._12sdp))) {

        SpacerHeight16()

        TrackingCodeTextField(
            userTrackingCode = userTrackingCode,
            onTextChanged = {
                onIntent(FindAppointmentIntent.CodeChanged(it))
            },
            onSearchButtonClicked = {
                onIntent(FindAppointmentIntent.Search)
            }
        )

        SpacerHeight16()

        HorizontalDivider(color = MaterialTheme.colorScheme.outline)

        SpacerHeight16()

        if (appointment != null) {
            AppointmentCard(
                appointment,
                onDelete = {
                    onIntent(FindAppointmentIntent.ShowStatusDeleteBottomSheet(true))
                }
            )
        } else {
            Box(Modifier.fillMaxWidth().weight(1f)) {
                Image(
                    painter = painterResource(R.drawable.logo_blue),
                    contentDescription = null,
                    modifier = Modifier
                        //.size(200.dp)
                        .align(alignment = Alignment.Center)
                        .graphicsLayer {
                            rotationZ = -45f
                            alpha = 0.4f
                        }
                )
            }
        }
    }
}

@Composable
private fun TrackingCodeTextField(
    userTrackingCode: StandardTextFieldState,
    onTextChanged: (String) -> Unit,
    onSearchButtonClicked: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppTextField(
            columnModifier = Modifier.weight(1f),
            value = userTrackingCode.text,
            onValueChange = {
                if (it.isBlank() || (it.isDigitsOnly() && it.length <= 10))
                    onTextChanged(it)
            },
            label = UiText.StringResource(R.string.str_enter_tracking_code),
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Search,
            onImeAction = {
                onSearchButtonClicked()
            },
            error = userTrackingCode.error
        )

        AppSpacerWidth(com.intuit.sdp.R.dimen._8sdp)

        SquareButton(
            modifier = Modifier.padding(top = dimensionResource(com.intuit.sdp.R.dimen._6sdp)),
            onClick = onSearchButtonClicked
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConfirmDeleteBottomSheet(
    onClickButton: (Boolean) -> Unit
) {

    ModalBottomSheet(
        containerColor = MaterialTheme.colorScheme.surface,
        scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f),
        onDismissRequest = {
            onClickButton(false)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(com.intuit.sdp.R.dimen._12sdp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SpacerHeight16()
            Text(
                text = stringResource(R.string.str_delete_warning),
                style = MaterialTheme.typography.bodyLarge
            )

            SpacerHeight16()

            HorizontalDivider(color = MaterialTheme.colorScheme.outline)

            SpacerHeight16()

            AppTwoButton(
                acceptBtnLabel = UiText.StringResource(R.string.delete),
                onAcceptButtonClick = {
                    onClickButton(true)
                },
                onCancelButtonClick = {
                    onClickButton(false)
                }
            )
            SpacerHeight16()
        }
    }
}

@Composable
private fun SpacerHeight16() {
    AppSpacerHeight(com.intuit.sdp.R.dimen._16sdp)
}