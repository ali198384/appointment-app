package feature.appointment.presentation.find.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import core.common.model.Appointment
import core.ui.R
import core.ui.component.AppSpacerHeight
import core.ui.datetime.toPersianDateTime
import core.ui.text.UiText
import core.ui.text.asString
import core.ui.theme.AppointmentTheme
import java.time.LocalDateTime

@Composable
fun AppointmentCard(
    item: Appointment,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
    ) {
        Box {
            IconButton(
                modifier = Modifier
                    .padding(dimensionResource(com.intuit.sdp.R.dimen._4sdp))
                    .align(Alignment.TopEnd),
                onClick = onDelete
            ) {
                Icon(
                    Icons.Rounded.Delete,
                    null,
                    tint = MaterialTheme.colorScheme.error
                )
            }

            Column(modifier = Modifier.padding(dimensionResource(com.intuit.sdp.R.dimen._12sdp))) {

                DoctorDetail(
                    item.doctorImage,
                    item.doctorName,
                    specialtyTitles = item.specialtyTitles.joinToString()
                )

                Spacer12()

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                Spacer12()

                TwoHorizontalText(
                    text1 = UiText.StringResource(R.string.str_patient_name),
                    text2 = item.patientName
                )

                Spacer12()

                TwoHorizontalText(
                    text1 = UiText.StringResource(R.string.str_date_time),
                    text2 = item.dateTime.toPersianDateTime()
                )
            }
        }
    }
}

@Composable
private fun Spacer12() {
    AppSpacerHeight(com.intuit.sdp.R.dimen._12sdp)
}

@Composable
private fun DoctorDetail(
    doctorImageUrl: String?,
    doctorName: String,
    specialtyTitles: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppSpacerHeight(com.intuit.sdp.R.dimen._8sdp)

        AsyncImage(
            model = doctorImageUrl ?: R.drawable.user_profile,
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(com.intuit.sdp.R.dimen._64sdp))
                .clip(CircleShape)
                .border(
                    width = dimensionResource(com.intuit.sdp.R.dimen._1sdp),
                    color = MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                )
        )

        Spacer12()

        Text(
            text = doctorName,
            style = MaterialTheme.typography.bodyLarge
        )

        AppSpacerHeight(com.intuit.sdp.R.dimen._8sdp)

        Text(
            text = specialtyTitles,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun TwoHorizontalText(
    modifier: Modifier = Modifier,
    text1: UiText,
    text2: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = text1.asString(),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = text2,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

@Preview
@Composable
private fun FiltersButtonPreview() {
    AppointmentTheme(darkTheme = true) {
        CompositionLocalProvider(
            LocalLayoutDirection provides (LayoutDirection.Rtl)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                AppointmentCard(
                    Appointment(
                        1L,
                        "علی مهماندوست",
                        "1234567890",
                        LocalDateTime.now(),
                        "مصطفی جوینده",
                        null,
                        listOf(
                            "پزشک عمومی",
                            "قلب و عروق",
                            "پوست و مو",
                            "چشم پزشکی"
                        )
                    )
                ) {}
            }
        }
    }
}