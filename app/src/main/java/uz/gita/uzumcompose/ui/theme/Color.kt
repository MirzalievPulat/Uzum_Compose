package uz.gita.uzumcompose.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import uz.gita.uzumcompose.R

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


val  Color.Companion.PinkUzum :Color get() =  Color(0xFFCD25DC)
val  Color.Companion.BlackUzum :Color get() =  Color(0xFF101010)
val  Color.Companion.HintUzum :Color get() =  Color(0xFFAFAFAF)
val  Color.Companion.TextField :Color get() =  Color(0xFFEAECEE)
/*<color name="text_primary">#101010</color>
    <color name="text_hint">#AFAFAF</color>
    <color name="text_pink">#CD25DC</color>*/

val fontFamilyUzum = FontFamily(
    Font(R.font.tt_uzum_medium, FontWeight.Medium),
    Font(R.font.tt_uzum_demibold, FontWeight.SemiBold),
    Font(R.font.tt_uzum_bold, FontWeight.Bold),
)