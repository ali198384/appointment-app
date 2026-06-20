package core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import core.ui.text.UiText

@Composable
fun <T> SearchableListScreen(
    query: String,
    onQueryChange: (String) -> Unit,
    items: List<T>,
    loading: Boolean,
    modifier: Modifier = Modifier,
    label: UiText? = null,
    key: ((T) -> Any)? = null,
    emptyContent: @Composable (() -> Unit)? = null,
    itemContent: @Composable (T) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {

        // Search box
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            AppTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(com.intuit.sdp.R.dimen._8sdp)),
                label = label,
                singleLine = true,
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(dimensionResource(id = com.intuit.sdp.R.dimen._24sdp))
                            .padding(horizontal = dimensionResource(id = com.intuit.sdp.R.dimen._3sdp)),
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.surfaceTint
                    )
                }
            )
        }

        HorizontalDivider(
            thickness = dimensionResource(com.intuit.sdp.R.dimen._1sdp),
            color = MaterialTheme.colorScheme.outlineVariant
        )

        // Loading
        if (loading) {
            AppSpacerHeight(com.intuit.sdp.R.dimen._8sdp)
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            AppSpacerHeight(com.intuit.sdp.R.dimen._6sdp)
        }

        // Empty
        if (!loading && items.isEmpty()) {

            emptyContent?.invoke() ?: Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Text(
                    text = stringResource(core.ui.R.string.str_not_found_record),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            return@Column
        }

        // List
        LazyColumn() {
            if (key != null) {
                items(
                    items = items,
                    key = key
                ) { item ->
                    itemContent(item)
                }
            } else {
                items(items) { item ->
                    itemContent(item)
                }
            }
        }
    }
}
