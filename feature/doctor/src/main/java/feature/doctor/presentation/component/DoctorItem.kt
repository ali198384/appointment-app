package feature.doctor.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import core.common.model.Doctor
import core.common.model.TimeSlot
import core.ui.component.AppSpacerHeight
import core.ui.text.UiText
import core.ui.text.asString
import core.ui.theme.AppointmentTheme
import feature.doctor.R
import java.time.LocalDateTime

@Composable
fun DoctorItem(
    item: Doctor,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(com.intuit.sdp.R.dimen._4sdp)),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(com.intuit.sdp.R.dimen._8sdp)),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = item.imageUrl ?: core.ui.R.drawable.user_profile,
                contentDescription = null,
                modifier = Modifier
                    .size(dimensionResource(com.intuit.sdp.R.dimen._56sdp))
                    .clip(CircleShape)
                    .border(
                        width = dimensionResource(com.intuit.sdp.R.dimen._1sdp),
                        color = MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
            )

            Spacer(Modifier.width(12.dp))

            Column {

                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold
                )

                AppSpacerHeight(com.intuit.sdp.R.dimen._4sdp)

                Text(
                    text = item.specialtyTitles.joinToString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                AppSpacerHeight(com.intuit.sdp.R.dimen._4sdp)

                Text(
                    text = UiText.StringResource(
                        R.string.str_first_appointment,
                        item.nextAvailableTimeSlot?.timeText ?: ""
                    ).asString(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    }
}

@Preview
@Composable
private fun DoctorItemPreview() {
    AppointmentTheme() {
        CompositionLocalProvider(
            LocalLayoutDirection provides (LayoutDirection.Rtl)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(dimensionResource(com.intuit.sdp.R.dimen._4sdp))
            ) {
                DoctorItem(
                    Doctor(
                        1L, "دکتر مصطفی جوینده", null,
                        listOf(
                            "پزشک عمومی",
                            "قلب و عروق",
                            "پوست و مو",
                        ),
                        TimeSlot(
                            1,
                            LocalDateTime.now(),
                            "چهارشنبه، 22 بهمن - 11:30"
                        )
                    )
                ) {}

                DoctorItem(
                    Doctor(
                        1L, "دکتر مصطفی جوینده", null,
                        listOf(
                            "پزشک عمومی",
                            "قلب و عروق",
                            "پوست و مو",
                        ),
                        TimeSlot(
                            1,
                            LocalDateTime.now(),
                            "چهارشنبه، 22 بهمن - 11:30"
                        )
                    )
                ) {}
            }
        }
    }
}
