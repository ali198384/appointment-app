package core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import core.ui.R
import core.ui.model.ButtonStyleType
import core.ui.text.UiText
import core.ui.theme.AppointmentTheme


@Composable
fun AppTwoButton (
    modifier: Modifier = Modifier,
    acceptBtnLabel: UiText = UiText.StringResource(R.string.str_save),
    cancelBtnLabel: UiText = UiText.StringResource(R.string.str_cancel),
    acceptButtonEnabled: Boolean = true,
    onCancelButtonClick: () -> Unit,
    onAcceptButtonClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        AppButton(
            modifier = Modifier.weight(1f),
            buttonStyleType = ButtonStyleType.PRIMARY_TYPE,
            title = acceptBtnLabel,
            enabled = acceptButtonEnabled,
            onclick = onAcceptButtonClick
        )
        AppSpacerWidth(com.intuit.sdp.R.dimen._8sdp)

        AppButton(
            modifier = Modifier.weight(1f),
            buttonStyleType = ButtonStyleType.SURFACE_TYPE,
            title = cancelBtnLabel,
            onclick = onCancelButtonClick
        )
    }
}

@Preview
@Composable
private fun AppTwoButtonPreview() {
    AppointmentTheme {
        CompositionLocalProvider(LocalLayoutDirection provides (LayoutDirection.Rtl)) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
                verticalAlignment = Alignment.Bottom
            ) {
                AppTwoButton(
                    onCancelButtonClick = {}, onAcceptButtonClick = {}
                )
            }
        }
    }
}