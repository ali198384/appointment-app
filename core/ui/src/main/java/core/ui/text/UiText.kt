package core.ui.text

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

sealed class UiText {

    data class Dynamic(val value: String) : UiText()

    class StringResource(
        @param:StringRes val resId: Int,
        vararg val args: Any
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is Dynamic -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}

@Composable
fun UiText.asString(): String = asString(LocalContext.current)
