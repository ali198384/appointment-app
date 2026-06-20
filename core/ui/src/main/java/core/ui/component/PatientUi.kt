package core.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.core.text.isDigitsOnly
import core.ui.R
import core.ui.model.Gender
import core.ui.model.StandardTextFieldState
import core.ui.text.UiText


@Composable
fun PatientUi(
    name: StandardTextFieldState,
    age: StandardTextFieldState,
    nationalCode: StandardTextFieldState,
    mobileNumber: StandardTextFieldState,
    gender: Gender,
    onNameChanged: (String) -> Unit,
    onAgeChanged: (String) -> Unit,
    onNationalCodeChanged: (String) -> Unit,
    onMobileNumberChanged: (String) -> Unit,
    onGenderChanged: (Gender) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NameAndAgeTextField(
            nameValue = name,
            ageValue = age,
            onNameChanged = onNameChanged,
            onAgeChanged = onAgeChanged,
        )

        SpacerHeight16()

        NationalCodeAndMobileNumberTextField(
            nationalCodeValue = nationalCode,
            phoneNumberValue = mobileNumber,
            onNationalCodeChanged = onNationalCodeChanged,
            onMobileNumberChanged = onMobileNumberChanged,
        )

        SpacerHeight16()

        HandleGender(
            genderValue = gender,
            onGenderChanged = onGenderChanged
        )
    }
}

@Composable
private fun SpacerHeight16() {
    AppSpacerHeight(com.intuit.sdp.R.dimen._16sdp)
}

@Composable
private fun NameAndAgeTextField(
    nameValue: StandardTextFieldState,
    ageValue: StandardTextFieldState,
    onNameChanged: (String) -> Unit,
    onAgeChanged: (String) -> Unit
) {
    HorizontalTwoTextField(
        firstWeight = 2f,
        label1 = UiText.StringResource(R.string.str_name),
        label2 = UiText.StringResource(R.string.str_age),
        value1 = nameValue,
        value2 = ageValue,
        keyboardType2 = KeyboardType.Decimal,
        onValueChange1 = {
            onNameChanged(it)
        },
        onValueChange2 = {
            if (it.isBlank() || (it.isDigitsOnly() && it.length <= 3 && it.toInt() <= 150))
                onAgeChanged(it)
        }
    )
}

@Composable
fun NationalCodeAndMobileNumberTextField(
    nationalCodeValue: StandardTextFieldState,
    phoneNumberValue: StandardTextFieldState,
    onNationalCodeChanged: (String) -> Unit,
    onMobileNumberChanged: (String) -> Unit
) {
    HorizontalTwoTextField(
        label1 = UiText.StringResource(R.string.str_national_code),
        label2 = UiText.StringResource(R.string.str_phone_number),
        value1 = nationalCodeValue,
        value2 = phoneNumberValue,
        keyboardType1 = KeyboardType.Decimal,
        keyboardType2 = KeyboardType.Phone,
        onValueChange1 = {
            if (it.isBlank() || (it.isDigitsOnly() && it.length <= 10))
                onNationalCodeChanged(it)
        },
        onValueChange2 = {
            if (it.isBlank() || (it.isDigitsOnly() && it.length <= 11))
                onMobileNumberChanged(it)
        }
    )
}

@Composable
private fun HandleGender(
    genderValue: Gender,
    onGenderChanged: (Gender) -> Unit
) {

    Row(
        Modifier
            .fillMaxWidth()
            .border(
                width = dimensionResource(id = com.intuit.sdp.R.dimen._1sdp),
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.medium
            )
            .padding(dimensionResource(com.intuit.sdp.R.dimen._6sdp))
    ) {

        Text(
            text = stringResource(core.ui.R.string.str_gender),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )

        AppSpacerWidth(com.intuit.sdp.R.dimen._16sdp)

        AppRadioGroup(
            modifier = Modifier.weight(2f),
            options = Gender.entries,
            selected = genderValue,
            onSelect = {
                onGenderChanged(it)
            },
            labelProvider = {
                it.displayResId
            },
            horizontal = true
        )
    }
}
