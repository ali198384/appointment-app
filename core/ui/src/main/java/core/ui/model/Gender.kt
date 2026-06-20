package core.ui.model

import androidx.annotation.StringRes
import core.ui.R

enum class Gender(@param:StringRes val displayResId: Int) {
    MALE(R.string.str_male),
    FEMALE(R.string.str_female);

    companion object {
        fun convertToGender(isMale: Boolean): Gender =
            if (isMale) MALE else FEMALE

        fun toBoolean(gender: Gender): Boolean = gender == MALE
    }
}