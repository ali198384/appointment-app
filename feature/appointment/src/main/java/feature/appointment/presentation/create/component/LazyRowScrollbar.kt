package feature.appointment.presentation.create.component

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

fun Modifier.lazyRowScrollbar(
    state: LazyListState,
    height: Dp = 4.dp,
    minThumbWidth: Dp = 24.dp
) = composed {

    val layoutDirection = LocalLayoutDirection.current
    val scrollColor = MaterialTheme.colorScheme.outline

    drawWithContent {

        drawContent()

        val info = state.layoutInfo
        val visible = info.visibleItemsInfo

        if (visible.isEmpty()) return@drawWithContent

        val totalItems = info.totalItemsCount
        if (visible.size >= totalItems) return@drawWithContent

        val viewportWidth = size.width

        val first = visible.first()

        val firstItemSize = first.size.toFloat()
        val scrolledPx =
            first.index * firstItemSize - first.offset

        val totalContentPx = totalItems * firstItemSize

        val proportion = viewportWidth / totalContentPx

        val thumbWidth =
            (viewportWidth * proportion)
                .coerceAtLeast(minThumbWidth.toPx())

        val maxScrollPx = totalContentPx - viewportWidth
        val scrollFraction = scrolledPx / maxScrollPx

        val rawOffset =
            (viewportWidth - thumbWidth) * scrollFraction

        val offsetX =
            if (layoutDirection == LayoutDirection.Rtl)
                viewportWidth - thumbWidth - rawOffset
            else
                rawOffset

        drawRoundRect(
            color = scrollColor,
            topLeft = Offset(offsetX, size.height - (height - 12.dp).toPx()),
            size = Size(thumbWidth, height.toPx()),
            cornerRadius = CornerRadius(50f)
        )
    }
}