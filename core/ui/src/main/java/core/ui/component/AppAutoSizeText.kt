package core.ui.component


import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import core.ui.theme.fontDimensionResource

sealed class AutoSizeConstraint(open val min: TextUnit = TextUnit.Unspecified) {
    data class Width(override val min: TextUnit = TextUnit.Unspecified) : AutoSizeConstraint(min)
    data class Height(override val min: TextUnit = TextUnit.Unspecified) : AutoSizeConstraint(min)
}

@Composable
fun AppAutoSizeText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = 1,
    style: TextStyle = LocalTextStyle.current,
    constraint: AutoSizeConstraint = AutoSizeConstraint.Height(fontDimensionResource(id = com.intuit.sdp.R.dimen._8sdp)),
) {
    var textStyle by remember(text) { mutableStateOf(style) }
    var readyToDraw by remember(text) { mutableStateOf(false) }
    Text(
        modifier = modifier.drawWithContent {
            if (readyToDraw) drawContent()
        },
        text = text,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        style = textStyle,
        onTextLayout = { result ->
            fun constrain() {
                val reducedSize = textStyle.fontSize * 0.9
                if (constraint.min != TextUnit.Unspecified && reducedSize <= constraint.min) {
                    textStyle.copy(fontSize = constraint.min)
                    readyToDraw = true
                } else {
                    textStyle.copy(fontSize = textStyle.fontSize * 0.9)
                }
            }
            when (constraint) {
                is AutoSizeConstraint.Height -> {
                    if (result.didOverflowHeight) {
                        constrain()
                    } else {
                        readyToDraw = true
                    }
                }

                is AutoSizeConstraint.Width -> {
                    if (result.didOverflowWidth) {
                        constrain()
                    } else {
                        readyToDraw = true
                    }
                }
            }
        }
    )
}

@Composable
fun AppAutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = 1,
    style: TextStyle = LocalTextStyle.current,
    constraint: AutoSizeConstraint = AutoSizeConstraint.Height(fontDimensionResource(id = com.intuit.sdp.R.dimen._8sdp)),
) {
    AppAutoSizeText(
        modifier = modifier,
        text = AnnotatedString(text),
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        style = style,
        constraint = constraint
    )
}