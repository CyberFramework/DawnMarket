package test.teamfresh.dawnmarket.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class CustomTypography(
    val font17B: TextStyle,
    val font16B: TextStyle,
    val font13B: TextStyle,
    val font15M: TextStyle,
    val font13M: TextStyle,
    val font12M: TextStyle,
    val font15: TextStyle,
    val font14: TextStyle,
    val font13: TextStyle,
)

val LocalCustomTypography = staticCompositionLocalOf {
    CustomTypography(
        font17B = TextStyle.Default,
        font16B = TextStyle.Default,
        font13B = TextStyle.Default,
        font15M = TextStyle.Default,
        font13M = TextStyle.Default,
        font12M = TextStyle.Default,
        font15 = TextStyle.Default,
        font14 = TextStyle.Default,
        font13 = TextStyle.Default,
    )
}

val Typography = CustomTypography(
    font17B = TextStyle(
        fontWeight = FontWeight(700),
        fontSize = 17.sp,
        lineHeight = 24.sp,
    ),
    font16B = TextStyle(
        fontWeight = FontWeight(700),
        fontSize = 16.sp,
        lineHeight = 21.sp,
    ),
    font13B = TextStyle(
        fontWeight = FontWeight(700),
        fontSize = 13.sp,
        lineHeight = 16.sp,
    ),
    font15M = TextStyle(
        fontWeight = FontWeight(500),
        fontSize = 15.sp,
        lineHeight = 20.sp,
    ),
    font13M = TextStyle(
        fontWeight = FontWeight(500),
        fontSize = 13.sp,
        lineHeight = 16.sp,
    ),
    font12M = TextStyle(
        fontWeight = FontWeight(500),
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    font15 = TextStyle(
        fontWeight = FontWeight(400),
        fontSize = 15.sp,
        lineHeight = 20.sp,
    ),
    font14 = TextStyle(
        fontWeight = FontWeight(400),
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    font13 = TextStyle(
        fontWeight = FontWeight(400),
        fontSize = 13.sp,
        lineHeight = 16.sp,
    ),
)