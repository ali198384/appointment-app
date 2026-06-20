package core.ui.theme

import androidx.annotation.DimenRes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.intuit.sdp.R

// Set of Material typography styles to start with
val appFontFamily = FontFamily(Font(core.ui.R.font.iransans_mono_space_num))

@Composable
fun appTypography(): Typography = Typography(

    headlineLarge = TextStyle(
        fontSize = fontDimensionResource(R.dimen._20sdp),
        lineHeight = fontDimensionResource(R.dimen._20sdp),
        fontFamily = appFontFamily
    ),

    headlineMedium = TextStyle(
        fontSize = fontDimensionResource(R.dimen._16sdp),
        lineHeight = fontDimensionResource(R.dimen._16sdp),
        fontFamily = appFontFamily
    ),

    titleLarge = TextStyle(
        fontSize = fontDimensionResource(R.dimen._14sdp),
        lineHeight = fontDimensionResource(R.dimen._14sdp),
        fontFamily = appFontFamily
    ),

    bodyLarge = TextStyle(
        fontSize = fontDimensionResource(R.dimen._12sdp),
        lineHeight = fontDimensionResource(R.dimen._12sdp),
        fontFamily = appFontFamily
    ),

    bodyMedium = TextStyle(
        fontSize = fontDimensionResource(R.dimen._11sdp),
        lineHeight = fontDimensionResource(R.dimen._11sdp),
        fontFamily = appFontFamily
    ),

    labelLarge = TextStyle(
        fontSize = fontDimensionResource(R.dimen._10sdp),
        lineHeight = fontDimensionResource(R.dimen._10sdp),
        fontFamily = appFontFamily
    )
)

@Composable
@ReadOnlyComposable
fun fontDimensionResource(@DimenRes id: Int) = dimensionResource(id = id).value.sp