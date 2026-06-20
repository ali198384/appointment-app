@file:OptIn(ExperimentalFoundationApi::class)

package core.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import coil.compose.AsyncImage
import kotlinx.coroutines.delay

@Composable
fun ImageSlider(
    images: List<Int>,
    modifier: Modifier = Modifier,
    slideDuration: Long = 3000L,
    onSlideClicked: () -> Unit
) {
    if (images.isEmpty()) return

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { images.size }
    )

    // Auto slide effect (memory safe)
    LaunchedEffect(pagerState) {
        while (true) {
            delay(slideDuration)

            val next = (pagerState.currentPage + 1) % images.size
            pagerState.animateScrollToPage(next)
        }
    }

    Box(modifier = modifier) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->

            AsyncImage(
                model = images[page],
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSlideClicked()
                    }
            )
        }

        // optional: indicator
        DotsIndicator(
            totalDots = images.size,
            selectedIndex = pagerState.currentPage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(dimensionResource(com.intuit.sdp.R.dimen._6sdp))
        )
    }
}

@Composable
private fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(totalDots) { index ->
            Box(
                Modifier
                    .padding(dimensionResource(com.intuit.sdp.R.dimen._3sdp))
                    .size(
                        dimensionResource(
                            if (index == selectedIndex)
                                com.intuit.sdp.R.dimen._8sdp
                            else
                                com.intuit.sdp.R.dimen._6sdp
                        )
                    )
                    .background(
                        color = if (index == selectedIndex)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.outline,
                        shape = MaterialTheme.shapes.small
                    )
            )
        }
    }
}