package core.ui.component

import androidx.annotation.DimenRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import core.ui.enableOrDisableComponent
import core.ui.model.ButtonStyleType
import core.ui.text.UiText
import core.ui.text.asString
import core.ui.theme.AppointmentTheme

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    buttonStyleType: ButtonStyleType,
    elevation: ButtonElevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
    title: UiText = UiText.Dynamic(""),
    enabled: Boolean = true,
    imageVector: ImageVector? = null,
    @DimenRes vectorDimenId: Int = com.intuit.sdp.R.dimen._12sdp,
    iconTint: Color? = null,
    useImageOwnTint: Boolean = false,
    onclick: () -> Unit,
) {
    val containerColor = when (buttonStyleType) {
        ButtonStyleType.PRIMARY_TYPE -> MaterialTheme.colorScheme.primary
        ButtonStyleType.SURFACE_TYPE -> MaterialTheme.colorScheme.surfaceVariant
    }

    val contentColor = when (buttonStyleType) {
        ButtonStyleType.PRIMARY_TYPE -> MaterialTheme.colorScheme.onPrimary
        ButtonStyleType.SURFACE_TYPE -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Button(
        onClick = onclick,
        modifier = modifier.height(dimensionResource(id = com.intuit.sdp.R.dimen._36sdp)),
        shape = MaterialTheme.shapes.medium,
        enabled = enabled,
        elevation = elevation,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = containerColor.copy(alpha = 0.5f),
            contentColor = contentColor
        ),
        border = BorderStroke(
            width = when (buttonStyleType) {
                ButtonStyleType.PRIMARY_TYPE -> 0.dp
                ButtonStyleType.SURFACE_TYPE -> dimensionResource(id = com.intuit.sdp.R.dimen._1sdp)
            },
            color = when (buttonStyleType) {
                ButtonStyleType.PRIMARY_TYPE -> MaterialTheme.colorScheme.onPrimary
                ButtonStyleType.SURFACE_TYPE -> MaterialTheme.colorScheme.onSurface
            }.copy(
                alpha = enableOrDisableComponent(enabled)
            )
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            val condition = when (buttonStyleType) {
                ButtonStyleType.PRIMARY_TYPE ->
                    MaterialTheme.colorScheme.onPrimary

                ButtonStyleType.SURFACE_TYPE ->
                    MaterialTheme.colorScheme.onSurface
            }.copy(
                alpha = enableOrDisableComponent(enabled)
            )

            val tint = if (useImageOwnTint) Color.Unspecified
            else iconTint ?: condition

            if (imageVector != null) {
                Icon(
                    modifier = Modifier.size(dimensionResource(id = vectorDimenId)),
                    imageVector = imageVector,
                    contentDescription = "",
                    tint = tint
                )
            }
            AppSpacerWidth(com.intuit.sdp.R.dimen._6sdp)

            AppAutoSizeText(
                text = title.asString(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview
@Composable
private fun AppButtonPreview() {
    AppointmentTheme(darkTheme = false) {
        CompositionLocalProvider(
            LocalLayoutDirection provides (LayoutDirection.Rtl)
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(10.dp)
            ) {
                AppButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonStyleType = ButtonStyleType.SURFACE_TYPE,
                    title = UiText.Dynamic("ذخیره"),
                    imageVector = Icons.Rounded.Settings,
                ) {}
                AppSpacerHeight(com.intuit.sdp.R.dimen._8sdp)
                AppButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonStyleType = ButtonStyleType.PRIMARY_TYPE,
                    title = UiText.Dynamic("ذخیره"),
                    imageVector = Icons.Rounded.Settings,
                ) {}
                AppSpacerHeight(com.intuit.sdp.R.dimen._8sdp)
                AppButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonStyleType = ButtonStyleType.SURFACE_TYPE,
                    title = UiText.Dynamic("ذخیره"),
                    imageVector = Icons.Rounded.Settings,
                    enabled = false,
                ) {}
                AppSpacerHeight(com.intuit.sdp.R.dimen._8sdp)
                AppButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonStyleType = ButtonStyleType.PRIMARY_TYPE,
                    title = UiText.Dynamic("ذخیره"),
                    imageVector = Icons.Rounded.Settings,
                    enabled = false
                ) {}

                AppSpacerHeight(com.intuit.sdp.R.dimen._8sdp)
                Text(
                    text = stringResource(core.ui.R.string.str_edit_profile),
                    style = MaterialTheme.typography.headlineLarge,
                )
            }
        }
    }
}