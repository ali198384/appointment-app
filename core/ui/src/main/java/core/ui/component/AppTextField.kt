package core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import core.ui.error.Error
import core.ui.error.TextFieldError
import core.ui.model.StandardTextFieldState
import core.ui.text.UiText
import core.ui.text.asString
import core.ui.theme.AppointmentTheme


@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    columnModifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    value: String,
    label: UiText? = null,
    placeholder: UiText? = null,
    maxLines: Int = 1,
    onValueChange: (String) -> Unit = { },
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    textAlign: TextAlign = TextAlign.Unspecified,
    textDirection: TextDirection = TextDirection.Unspecified,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    error: Error? = null
) {
    Column(modifier = columnModifier) {
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = modifier.fillMaxWidth().then(
                Modifier.defaultMinSize(minHeight = dimensionResource(id = com.intuit.sdp.R.dimen._42sdp))
            ),
            maxLines = maxLines,
            label = {
                if (label != null)
                    Text(
                        text = label.asString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
            },
            placeholder = {
                if (placeholder != null)
                    Text(
                        text = placeholder.asString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = imeAction,
                keyboardType = keyboardType
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onImeAction()
                    focusManager.clearFocus()
                },
                onSearch = {
                    onImeAction()
                    focusManager.clearFocus()
                }
            ),
            textStyle = textStyle.copy(
                textAlign = textAlign,
                textDirection = textDirection
            ),
            enabled = enabled,
            readOnly = readOnly,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = error != null,
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                errorTextColor = MaterialTheme.colorScheme.error,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
                errorPlaceholderColor = MaterialTheme.colorScheme.error,
                cursorColor = MaterialTheme.colorScheme.primary,
                errorCursorColor = MaterialTheme.colorScheme.error,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                errorBorderColor = MaterialTheme.colorScheme.error,
                ),
        )
        if (error != null) {
            CompositionLocalProvider(LocalLayoutDirection provides (LayoutDirection.Rtl)) {
                Text(
                    text = error.message.asString(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
@Preview
private fun CustomOutlineTextFieldPreview() {
    AppointmentTheme {
        CompositionLocalProvider(LocalLayoutDirection provides (LayoutDirection.Rtl)) {
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(Modifier
                    .fillMaxSize()
                    .padding(12.dp)) {
                    HorizontalTwoTextField(
                        label1 = UiText.Dynamic("نام"),
                        label2 = UiText.Dynamic("نام خانوادگی"),
                        value1 = StandardTextFieldState("علی"),
                        value2 = StandardTextFieldState("مهماندوست"),
                        onValueChange1 = {},
                        onValueChange2 = {},
                    )

                    AppSpacerHeight(com.intuit.sdp.R.dimen._12sdp)

                    AppTextField(
                        columnModifier = Modifier.fillMaxWidth(),
                        value = "",
                        label = UiText.Dynamic("جستجو"),
                        onValueChange = {},
                        error = TextFieldError.FieldEmpty
                    )

                    AppSpacerHeight(com.intuit.sdp.R.dimen._12sdp)

                    OutlinedTextField(
                        value = "سلام. خوبی؟",
                        onValueChange = {},
                        trailingIcon = {
                            Icon(
                                modifier = Modifier
                                    .size(dimensionResource(id = com.intuit.sdp.R.dimen._18sdp))
                                    .padding(dimensionResource(id = com.intuit.sdp.R.dimen._2sdp)),
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.surfaceTint
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun HorizontalTwoTextField(
    label1: UiText,
    label2: UiText,
    value1: StandardTextFieldState,
    value2: StandardTextFieldState,
    onValueChange1: (String) -> Unit,
    onValueChange2: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppTextField(
            columnModifier = Modifier.weight(1f),
            value = value1.text,
            onValueChange = onValueChange1,
            label = label1,
        )

        AppSpacerWidth(com.intuit.sdp.R.dimen._12sdp)

        AppTextField(
            columnModifier = Modifier.weight(1f),
            value = value2.text,
            onValueChange = onValueChange2,
            label = label2
        )
    }
}