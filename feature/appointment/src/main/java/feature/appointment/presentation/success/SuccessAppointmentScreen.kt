package feature.appointment.presentation.success

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import core.ui.R
import core.ui.component.AppButton
import core.ui.component.AppSpacerHeight
import core.ui.component.SquareButton
import core.ui.effect.CollectEffect
import core.ui.effect.UiEffect
import core.ui.model.ButtonStyleType
import core.ui.text.UiText
import core.ui.theme.AppointmentTheme
import feature.appointment.presentation.create.mvi.CreateAppointmentIntent
import feature.appointment.presentation.success.mvi.SuccessAppointmentIntent

@Composable
fun SuccessAppointmentScreen(
    onNavigateHome: () -> Unit,
    vm: SuccessAppointmentViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }

    BackHandler { }

    CollectEffect(vm.effect) { effect ->

        when (effect) {
            is UiEffect.Navigate -> onNavigateHome()
            
            is UiEffect.ShowSnackBar -> {

                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

                clipboard.setPrimaryClip(
                    ClipData.newPlainText(
                        "trackingCode",
                        state.trackingCode
                    )
                )

                snackBarHostState.showSnackbar(effect.uiText.asString(context))
            }
            
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        bottomBar = {
            BottomAppBar(
                contentPadding = PaddingValues(0.dp),
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                tonalElevation = dimensionResource(com.intuit.sdp.R.dimen._2sdp)
            ) {
                AppButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = com.intuit.sdp.R.dimen._12sdp)),
                    buttonStyleType = ButtonStyleType.PRIMARY_TYPE,
                    title = UiText.StringResource(R.string.str_understand)
                ) {
                    vm.sendIntent(SuccessAppointmentIntent.DoneClicked)
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        PageContent(
            padding,
            state.trackingCode,
            onCopyIconClicked = {
                vm.sendIntent(SuccessAppointmentIntent.CopyTrackingCode)
            }
        )
    }
}

@Composable
private fun PageContent(
    padding: PaddingValues,
    trackingCode: String,
    onCopyIconClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.vector_round_ok_green),
            contentDescription = "",
            modifier = Modifier.size(
                dimensionResource(com.intuit.sdp.R.dimen._100sdp)
            )
        )

        AppSpacerHeight(com.intuit.sdp.R.dimen._36sdp)

        Text(
            text = stringResource( R.string.str_success_submit_appointment),
            style = MaterialTheme.typography.headlineMedium
        )

        AppSpacerHeight(com.intuit.sdp.R.dimen._24sdp)

        Card(
            modifier = Modifier
                    .padding(
                    dimensionResource(com.intuit.sdp.R.dimen._12sdp)
                    )
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) {

            Row(
                modifier = Modifier.padding(dimensionResource(com.intuit.sdp.R.dimen._12sdp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.str_tracking_code),
                    style = MaterialTheme.typography.titleLarge
                )

                IconButton(onClick = onCopyIconClicked) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "copy"
                    )
                }

                Text(
                    text = trackingCode,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Preview
@Composable
private fun SuccessAppointmentScreenPreview() {
    AppointmentTheme(darkTheme = true) {
        CompositionLocalProvider(
            LocalLayoutDirection provides (LayoutDirection.Rtl)
        ) {
            Scaffold(
                bottomBar = {
                    BottomAppBar(
                        contentPadding = PaddingValues(0.dp),
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        tonalElevation = dimensionResource(com.intuit.sdp.R.dimen._2sdp)
                    ) {
                        AppButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimensionResource(id = com.intuit.sdp.R.dimen._12sdp)),
                            buttonStyleType = ButtonStyleType.PRIMARY_TYPE,
                            title = UiText.StringResource(R.string.str_understand)
                        ) {}
                    }
                },
                containerColor = MaterialTheme.colorScheme.background
            ) { padding ->
                PageContent(
                    padding,
                    "1234567890",
                ) {}
            }
        }
    }
}