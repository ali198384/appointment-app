package core.ui.component


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.intuit.sdp.R

@Composable
fun <T> AppRadioGroup(
    options: List<T>,
    selected: T?,
    onSelect: (T) -> Unit,
    labelProvider: (T) -> Int,
    modifier: Modifier = Modifier,
    horizontal: Boolean = false
) {

    if (horizontal) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly
            //horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen._16sdp))
        ) {
            options.forEach { item ->
                RadioItem(
                    text = stringResource(labelProvider(item)),
                    selected = item == selected,
                    onClick = { onSelect(item) }
                )
            }
        }
    } else {
        Column(modifier = modifier) {
            options.forEach { item ->
                RadioItem(
                    text = stringResource(labelProvider(item)),
                    selected = item == selected,
                    onClick = { onSelect(item) }
                )
            }
        }
    }
}

@Composable
private fun RadioItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = dimensionResource(R.dimen._4sdp))
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
