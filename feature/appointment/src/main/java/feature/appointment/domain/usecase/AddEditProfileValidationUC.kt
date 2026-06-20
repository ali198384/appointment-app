package feature.appointment.domain.usecase

import android.content.Context
import core.ui.checkMobileNoError
import core.ui.error.TextFieldError
import dagger.hilt.android.qualifiers.ApplicationContext
import core.ui.model.AddEditPatientResult
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import javax.inject.Inject

class AddEditPatientValidationUC @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    operator fun invoke(
        name: String,
        nationalCode: String,
        mobileNo: String,
        age: String
    ): AddEditPatientResult {
        var nameError: TextFieldError? = null
        if (name.isBlank())
            nameError = TextFieldError.FieldEmpty

        val phoneNumberUtil = PhoneNumberUtil.createInstance(context)

        val mobileNoError = if (mobileNo.isNotBlank()) {
            if (mobileNo.length < 11)
                TextFieldError.Invalid
            else {
                checkMobileNoError(phoneNumberUtil, mobileNo)
            }
        } else TextFieldError.FieldEmpty

        val nationalCodeError: TextFieldError? =
            if (nationalCode.isBlank())
                TextFieldError.FieldEmpty
            else if (nationalCode.length < 10)
                TextFieldError.Invalid
            else null

        val ageError: TextFieldError? =
            if (age.isBlank())
                TextFieldError.FieldEmpty
            else if (age.toInt() < 1)
                TextFieldError.Invalid
            else null

        return if (
            nameError != null ||
            mobileNoError != null ||
            nationalCodeError != null ||
            ageError != null
        )
            AddEditPatientResult(
                nameError = nameError,
                nationalCodeError = nationalCodeError,
                mobileNoError = mobileNoError,
                ageError = ageError,
            )
        else
            AddEditPatientResult(success = true)
    }
}
