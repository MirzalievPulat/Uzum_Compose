package uz.gita.uzumcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.uzumcompose.presentation.signUp.SignUpContract
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum

@Composable
fun AppTextButton(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.PinkUzum,
    textAlign: TextAlign = TextAlign.Center,
    clickEnabled:Boolean = true,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        color = color,
        fontFamily = fontFamilyUzum,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = textAlign,
        modifier = modifier
            .height(50.dp)
            .background(Color.Transparent, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable(enabled = clickEnabled){
                onClick.invoke()
            }
            .padding(horizontal = 16.dp)
            .wrapContentHeight(Alignment.CenterVertically)
    )

}

@Preview
@Composable
private fun Preview(modifier: Modifier = Modifier) {
    UzumComposeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(30.dp))
                AppTextButton(onClick = {}, text = "Sign in",)

            }
        }
    }

}