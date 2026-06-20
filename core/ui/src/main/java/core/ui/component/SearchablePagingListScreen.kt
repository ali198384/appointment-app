package core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import core.ui.text.UiText

@Composable
fun <T : Any> SearchablePagingListScreen(
    query: String,
    onQueryChange: (String) -> Unit,
    pagingItems: LazyPagingItems<T>,
    loading: Boolean,
    modifier: Modifier = Modifier,
    label: UiText? = null,
    itemContent: @Composable (T) -> Unit
) {

    Column(modifier = modifier.fillMaxSize()) {

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

        // Paging list
        LazyColumn(
            Modifier.padding(
                horizontal = dimensionResource(id = com.intuit.sdp.R.dimen._8sdp),
                vertical = dimensionResource(id = com.intuit.sdp.R.dimen._4sdp)
            )
        ) {

            items(pagingItems.itemCount) { index ->

                pagingItems[index]?.let {
                    itemContent(it)
                }
            }

            // Paging states
            pagingItems.apply {

                when {

                    loadState.refresh is LoadState.Loading -> {
                        item { LinearProgressIndicator() }
                    }

                    loadState.append is LoadState.Loading -> {
                        item { CircularProgressIndicator() }
                    }

                    loadState.refresh is LoadState.Error -> {
                        item {
                            Text(
                                text = stringResource(core.ui.R.string.str_old_info),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    itemCount == 0 &&
                            loadState.refresh !is LoadState.Loading -> {
                        item {
                            Text(
                                text = stringResource(core.ui.R.string.str_not_found_record),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
