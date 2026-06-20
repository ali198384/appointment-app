package feature.appointment.presentation.create.component

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import core.common.model.TimeSlot
import core.ui.dateTimeFormatter
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TimeSlotGrid(
    timeSlots: List<TimeSlot>,
    selected: Long?,
    onClick: (Long) -> Unit
) {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        items(timeSlots) { timeSlot ->
            OutlinedButton(
                onClick = { onClick(timeSlot.id) },
            ) {
                Text(
                    format.format(timeSlot.dateTime.dateTimeFormatter()),
                    color = if (selected == timeSlot.id)
                        MaterialTheme.colorScheme.primary
                    else Color.Unspecified
                )
            }
        }
    }
}
