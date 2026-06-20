package core.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import core.ui.error.TextFieldError
import io.michaelrocks.libphonenumber.android.NumberParseException
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import androidx.core.net.toUri


const val IRAN_COUNTRY_CODE = "+98"

fun enableOrDisableComponent(enabled: Boolean): Float {
    return if (enabled)
        1f
    else
        0.5f
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun checkMobileNoError(
    phoneNumberUtil: PhoneNumberUtil,
    mobileNumber: String
): TextFieldError? {
    var mobileError: TextFieldError? = null
    try {
        val mNumber1 = phoneNumberUtil.parse(IRAN_COUNTRY_CODE + mobileNumber, "ir")
        val type = phoneNumberUtil.getNumberType(mNumber1)
        if (
            type == PhoneNumberUtil.PhoneNumberType.FIXED_LINE ||
            !phoneNumberUtil.isValidNumber(mNumber1)
            ) {
            mobileError = TextFieldError.InvalidMobileNumber
        }
    } catch (e: NumberParseException) {
        mobileError = TextFieldError.InvalidMobileNumber
        e.printStackTrace()
    }
    return mobileError
}

fun Long.toLocalDateTime(): LocalDateTime =
    Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

fun LocalDateTime.fromLocalDateTime(): Long =
    this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun LocalDateTime.dateTimeFormatter(): String {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm") // 18:30
    //val dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd") // 2026/02/21

    return format(timeFormatter)
}

fun Context.openUrl(url: String) {
    startActivity(
        Intent(Intent.ACTION_VIEW, url.toUri())
    )
}