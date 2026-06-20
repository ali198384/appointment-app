package core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import core.ui.R
import core.ui.model.ButtonStyleType
import core.ui.text.UiText
import core.ui.theme.AppointmentTheme

@Composable
fun AppBottomBarConfirm(
    acceptBtnLabel: UiText = UiText.StringResource(R.string.str_save),
    cancelBtnLabel: UiText = UiText.StringResource(R.string.str_cancel),
    onCancelButtonClick: () -> Unit,
    onAcceptButtonClick: () -> Unit
) {
    BottomAppBar(
        contentPadding = PaddingValues(0.dp),
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        tonalElevation = dimensionResource(com.intuit.sdp.R.dimen._3sdp)
    ) {
        AppTwoButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = com.intuit.sdp.R.dimen._12sdp)),
            acceptBtnLabel = acceptBtnLabel,
            cancelBtnLabel = cancelBtnLabel,
            onCancelButtonClick = onCancelButtonClick,
            onAcceptButtonClick = onAcceptButtonClick
        )
    }
}

@Preview
@Composable
private fun AppBottomBarConfirmPreview() {
    AppointmentTheme(darkTheme = true) {
        CompositionLocalProvider(LocalLayoutDirection provides (LayoutDirection.Rtl)) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
                verticalAlignment = Alignment.Bottom
            ) {
                AppBottomBarConfirm(
                    onCancelButtonClick = {}, onAcceptButtonClick = {}
                )
            }
        }
    }
}