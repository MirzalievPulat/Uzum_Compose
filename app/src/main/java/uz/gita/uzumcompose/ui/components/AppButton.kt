package uz.gita.uzumcompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.PinkUzumPlain
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum


@Composable
fun AppButton(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    showProgress:Boolean = false,
    enabled:Boolean = true,
    containerColor:Color = Color.PinkUzum,
    onClick: () -> Unit
) {
    Button(modifier = modifier
        .height(50.dp),
        shape = RoundedCornerShape(16.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = Color.PinkUzumPlain,
            containerColor = containerColor,
            disabledContentColor = Color.White
        ),
        onClick = onClick) {

        if (showProgress) {
            CircularProgressIndicator(
                color = Color.White,
                strokeCap = StrokeCap.Square,
                strokeWidth = 4.dp,
                modifier = Modifier.scale(0.5f)
            )
        } else {
            Text(
                text = text,
                style = textStyle.copy(
                    fontFamily = fontFamilyUzum,
                    fontWeight = FontWeight.Medium 
                ),
                
            )
        }

    }
}

@Preview
@Composable
private fun Preview(modifier: Modifier = Modifier) {
    UzumComposeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column {
                Spacer(modifier = Modifier.height(30.dp))
                AppButton(onClick = {}, text = "Continue",)
            }
        }
    }

}