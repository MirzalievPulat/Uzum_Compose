package uz.gita.uzumcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.TextField
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum

//import uz.bellissimo.courier.app.R
//import uz.bellissimo.courier.ui.theme.CourierAppTheme
//import uz.bellissimo.courier.ui.theme.getColor
//import uz.bellissimo.courier.ui.theme.lines
//import uz.bellissimo.courier.ui.theme.mTypography

@Composable
fun PinViewComponent(
    label: String = "",
    pinText: String = "",
    onPinTextChanged: (String) -> Unit = {},
    digitCount: Int = 6,
    error: String? = null,
    focusRequester: FocusRequester = FocusRequester()
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (label != "") {
            Text(
                label,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
        Column(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            BasicTextField(
                value = pinText,
                onValueChange = {
                    if (it.length <= digitCount && it.isDigitsOnly()) onPinTextChanged(it)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                decorationBox = {
                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        repeat(digitCount) { index ->
                            DigitView(index, pinText,error != null)
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
//                    it.invoke()
                },
                modifier = Modifier.focusRequester(focusRequester)
            )

            error?.let {
                Spacer(Modifier.height(12.dp))
                Text(text = error,
                    fontFamily = fontFamilyUzum,
                    fontWeight = FontWeight.Medium,
                    color = Color.Red,
                    fontSize = 13.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
            }
        }

    }
}

@Preview
@Composable
fun PinViewComponentPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var p by remember { mutableStateOf("") }

        PinViewComponent(
            label = "Po'lat",
            error = "error",
            onPinTextChanged = {
                p = it
            },
            pinText = p
        )
    }


}


@Composable
private fun DigitView(
    index: Int,
    pinText: String,
    isError:Boolean
) {
    val modifier =
        Modifier
            .height(50.dp)
            .width(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color.TextField)

    Box(
        modifier = modifier
    ) {
        Text(
            text = if (index >= pinText.length) "" else pinText[index].toString(),
            modifier = Modifier.align(Alignment.Center),
            fontFamily = fontFamilyUzum,
            fontWeight = FontWeight.Medium,
            color = if (isError) Color.Red else Color.BlackUzum
        )
    }
}


