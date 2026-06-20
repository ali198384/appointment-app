package core.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.intuit.sdp.R
import core.ui.model.StandardTextFieldState
import core.ui.text.UiText

@Composable
fun HorizontalTwoTextField(
    firstWeight: Float = 1f,
    label1: UiText,
    label2: UiText,
    value1: StandardTextFieldState,
    value2: StandardTextFieldState,
    keyboardType1: KeyboardType = KeyboardType.Text,
    keyboardType2: KeyboardType = KeyboardType.Text,
    onValueChange1: (String) -> Unit,
    onValueChange2: (String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppTextField(
            columnModifier = Modifier.weight(firstWeight),
            value = value1.text,
            onValueChange = onValueChange1,
            label = label1,
            keyboardType = keyboardType1,
            error = value1.error
        )

        AppSpacerWidth(R.dimen._12sdp)

        AppTextField(
            columnModifier = Modifier.weight(1f),
            value = value2.text,
            onValueChange = onValueChange2,
            label = label2,
            keyboardType = keyboardType2,
            error = value2.error
        )
    }
}
