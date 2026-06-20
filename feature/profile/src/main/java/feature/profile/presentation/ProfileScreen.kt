package feature.profile.presentation

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import core.ui.R
import core.ui.component.AppBottomBarConfirm
import core.ui.component.AppImagePicker
import core.ui.component.AppSpacerHeight
import core.ui.component.AppToolbar
import core.ui.component.PatientUi
import core.ui.effect.CollectEffect
import core.ui.effect.UiEffect
import core.ui.model.Gender
import core.ui.model.StandardTextFieldState
import core.ui.view.LoadingView
import feature.profile.presentation.mvi.ProfileIntent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    vm: ProfileViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by vm.state.collectAsState()
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }

    CollectEffect(vm.effect) { effect ->
        when (effect) {
            is UiEffect.ShowSnackBar ->
                snackBarHostState.showSnackbar(effect.uiText.asString(context))

            is UiEffect.NavigateUp -> {
                onBack()
            }

            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            AppToolbar(
                titleRes = R.string.str_profile,
                onBackClick = onBack
            )
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        bottomBar = {
            AppBottomBarConfirm(
                onAcceptButtonClick = {
                    vm.sendIntent(ProfileIntent.Save)
                },
                onCancelButtonClick = onBack
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(Modifier.padding(padding)) {

            PageContent(
                imageUri = state.imageUri,
                name = state.name,
                age = state.age,
                nationalCode = state.nationalCode,
                mobileNumber = state.mobileNumber,
                gender = state.gender,
                onIntent = { onIntent ->
                    vm.sendIntent(onIntent)
                }
            )

            if (state.loading) {
                LoadingView()
            }
        }
    }
}

@Composable
private fun PageContent(
    imageUri: Uri?,
    name: StandardTextFieldState,
    age: StandardTextFieldState,
    nationalCode: StandardTextFieldState,
    mobileNumber: StandardTextFieldState,
    gender: Gender,
    onIntent: (ProfileIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(com.intuit.sdp.R.dimen._12sdp))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        SpacerHeight16()

        PageTitle()

        SpacerHeight32()

        HandleProfileImage(
            imageUri = imageUri,
            onImageChanged = onIntent
        )

        SpacerHeight32()

        PatientUi(
            name = name,
            age = age,
            nationalCode = nationalCode,
            mobileNumber = mobileNumber,
            gender = gender,
            onNameChanged = {
                onIntent(ProfileIntent.NameChanged(it))
            },
            onAgeChanged = {
                onIntent(ProfileIntent.AgeChanged(it))
            },
            onNationalCodeChanged = {
                onIntent(ProfileIntent.NationalChanged(it))
            },
            onMobileNumberChanged = {
                onIntent(ProfileIntent.MobileNumberChanged(it))
            },
            onGenderChanged = {
                onIntent(ProfileIntent.GenderChanged(it))
            }
        )

        SpacerHeight16()
    }
}

@Composable
private fun SpacerHeight16() {
    AppSpacerHeight(com.intuit.sdp.R.dimen._16sdp)
}

@Composable
private fun SpacerHeight32() {
    AppSpacerHeight(com.intuit.sdp.R.dimen._32sdp)
}

@Composable
private fun PageTitle() {
    Text(
        text = stringResource(R.string.str_edit_profile),
        style = MaterialTheme.typography.headlineLarge,
    )
}

@Composable
private fun HandleProfileImage(
    imageUri: Uri?,
    onImageChanged: (ProfileIntent) -> Unit
) {
    AppImagePicker(
        imageUri = imageUri,
        onImagePicked = {
            onImageChanged(ProfileIntent.ImagePicked(it))
        }
    )
}
