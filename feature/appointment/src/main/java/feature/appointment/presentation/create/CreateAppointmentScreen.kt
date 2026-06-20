package feature.appointment.presentation.create

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import core.common.model.Doctor
import core.ui.R
import core.ui.component.AppButton
import core.ui.component.AppRadioGroup
import core.ui.component.AppSpacerHeight
import core.ui.component.AppToolbar
import core.ui.component.AppTwoButton
import core.ui.component.PatientUi
import core.ui.datetime.DaySlotsUi
import core.ui.effect.CollectEffect
import core.ui.effect.UiEffect
import core.ui.model.ButtonStyleType
import core.ui.model.Gender
import core.ui.model.StandardTextFieldState
import core.ui.text.UiText
import core.ui.view.LoadingView
import feature.appointment.presentation.create.component.DayItem
import feature.appointment.presentation.create.component.TimeSlotChip
import feature.appointment.presentation.create.component.lazyRowScrollbar
import feature.appointment.presentation.create.mvi.CreateAppointmentIntent
import feature.appointment.presentation.create.mvi.CreateAppointmentState
import feature.appointment.presentation.create.mvi.PatientType

@Composable
fun CreateAppointmentScreen(
    onSuccess: (String) -> Unit,
    onBack: () -> Unit,
    vm: CreateAppointmentViewModel = hiltViewModel()
) {

    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val state by vm.state.collectAsState()

    CollectEffect(vm.effect) { effect ->
        when (effect) {
            is UiEffect.ShowSnackBar ->
                snackBarHostState.showSnackbar(effect.uiText.asString(context))

            is UiEffect.NavigateByString ->
                onSuccess(effect.value)

            else -> {}
        }
    }

    Scaffold(
        topBar = {
            AppToolbar(
                titleRes = R.string.str_submit_appointment,
                onBackClick = onBack
            )
        },
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
                    title = UiText.StringResource(R.string.str_submit_appointment)
                ) {
                    vm.sendIntent(CreateAppointmentIntent.Submit)
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Box(Modifier.padding(padding)) {

            PageContent(
                state = state,
                onIntent = { onIntent ->
                    vm.sendIntent(onIntent)
                }
            )

            if (state.loading) {
                LoadingView()
            }

            if (state.showPatientSheet) {
                AddPatientBottomSheet(
                    nameOther = state.nameOther,
                    ageOther = state.ageOther,
                    nationalCodeOther = state.nationalCodeOther,
                    mobileNumberOther = state.mobileNumberOther,
                    genderOther = state.genderOther,
                    onIntent = { intentOther ->
                        vm.sendIntent(intentOther)
                    }
                )
            }
        }
    }
}


@Composable
private fun PageContent(
    state: CreateAppointmentState,
    onIntent: (CreateAppointmentIntent) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(com.intuit.sdp.R.dimen._8sdp))
        //.verticalScroll(rememberScrollState())
    ) {

        SpacerHeight12()
        state.doctor?.let {
            DoctorDetail(it)
        }

        SpacerHeight12()

        HandlePatientType(
            patientType = state.patientType,
            onValueChange = onIntent
        )

        SpacerHeight12()

        HorizontalDivider(color = MaterialTheme.colorScheme.outline)

        SpacerHeight12()

        SelectTimeSlotScreen(
            daySlotList = state.daySlotList,
            selectedDayIndex = state.selectedDayIndex,
            selectedSlotId = state.selectedTimeSlotId,
            onDayClick = {
                onIntent(
                    CreateAppointmentIntent.SelectDaySlot(it)
                )
            },
            onSlotClick = {
                onIntent(
                    CreateAppointmentIntent.SelectTimeSlot(it)
                )
            }
        )

        SpacerHeight12()
    }
}

@Composable
private fun DoctorDetail(doctor: Doctor) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(dimensionResource(com.intuit.sdp.R.dimen._8sdp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = doctor.imageUrl ?: R.drawable.user_profile,
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(com.intuit.sdp.R.dimen._64sdp))
                .clip(CircleShape)
                .border(
                    width = dimensionResource(com.intuit.sdp.R.dimen._1sdp),
                    color = MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                )
        )

        SpacerHeight12()

        Text(
            text = doctor.name,
            style = MaterialTheme.typography.bodyLarge
        )

        AppSpacerHeight(com.intuit.sdp.R.dimen._8sdp)

        Text(
            text = doctor.specialtyTitles.joinToString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun HandlePatientType(
    patientType: PatientType,
    onValueChange: (CreateAppointmentIntent) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(dimensionResource(com.intuit.sdp.R.dimen._8sdp))
    ) {

        Text(
            text = stringResource(R.string.str_appointment_to),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )

        Row(horizontalArrangement = Arrangement.Center) {

            AppRadioGroup(
                modifier = Modifier.weight(4f),
                options = PatientType.entries,
                selected = patientType,
                onSelect = {
                    onValueChange(CreateAppointmentIntent.SelectPatientType(it))
                },
                labelProvider = {
                    it.displayResId
                },
                horizontal = true
            )

            Spacer(Modifier.weight(1f))
        }
    }
}

@Composable
private fun SelectTimeSlotScreen(
    daySlotList: List<DaySlotsUi>,
    selectedDayIndex: Int,
    selectedSlotId: Long?,
    onDayClick: (Int) -> Unit,
    onSlotClick: (Long) -> Unit
) {

    Column {
        val lazyState = rememberLazyListState()
        LazyRow(
            state = lazyState,
            modifier = Modifier.lazyRowScrollbar(lazyState),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            itemsIndexed(
                items = daySlotList,
                key = { _, item -> item.date }
            ) { index, day ->

                DayItem(
                    weekName = day.week,
                    dayOfMonth = day.dayOfMonth,
                    selected = index == selectedDayIndex,
                    onClick = { onDayClick(index) }
                )
            }
        }

        SpacerHeight12()

        val selectedDay = daySlotList.getOrNull(selectedDayIndex)

        // ---------- Slots ----------
        selectedDay?.let { day ->

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(
                    items = day.slots,
                    key = { it.id }
                ) { slot ->

                    TimeSlotChip(
                        text = slot.timeText,
                        selected = slot.id == selectedSlotId,
                        onClick = { onSlotClick(slot.id) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddPatientBottomSheet(
    nameOther: StandardTextFieldState,
    ageOther: StandardTextFieldState,
    nationalCodeOther: StandardTextFieldState,
    mobileNumberOther: StandardTextFieldState,
    genderOther: Gender,
    onIntent: (CreateAppointmentIntent) -> Unit
) {

    ModalBottomSheet(
        containerColor = MaterialTheme.colorScheme.surface,
        scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f),
        onDismissRequest = {
            onIntent(CreateAppointmentIntent.DismissSheet)
        },
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = false,
            shouldDismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(com.intuit.sdp.R.dimen._12sdp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            PatientUi(
                name = nameOther,
                age = ageOther,
                nationalCode = nationalCodeOther,
                mobileNumber = mobileNumberOther,
                gender = genderOther,
                onNameChanged = {
                    onIntent(CreateAppointmentIntent.NameChanged(it))
                },
                onAgeChanged = {
                    onIntent(CreateAppointmentIntent.AgeChanged(it))
                },
                onNationalCodeChanged = {
                    onIntent(CreateAppointmentIntent.NationalChanged(it))
                },
                onMobileNumberChanged = {
                    onIntent(CreateAppointmentIntent.MobileNumberChanged(it))
                },
                onGenderChanged = {
                    onIntent(CreateAppointmentIntent.GenderChanged(it))
                }
            )

            SpacerHeight12()

            HorizontalDivider(color = MaterialTheme.colorScheme.outline)

            SpacerHeight12()

            AppTwoButton(
                onAcceptButtonClick = {
                    onIntent(CreateAppointmentIntent.SaveNewPatient)
                },
                onCancelButtonClick = {
                    onIntent(CreateAppointmentIntent.DismissSheet)
                }
            )
            SpacerHeight12()
        }
    }
}

@Composable
fun SpacerHeight12() {
    AppSpacerHeight(com.intuit.sdp.R.dimen._12sdp)
}