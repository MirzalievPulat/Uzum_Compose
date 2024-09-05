package uz.gita.uzumcompose.utils.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//import uz.bellissimo.courier.app.R
//import uz.bellissimo.courier.ui.theme.CourierAppTheme
//import uz.bellissimo.courier.ui.theme.getColor
//import uz.bellissimo.courier.ui.theme.lines
//import uz.bellissimo.courier.ui.theme.mTypography

@Composable
fun PinViewComponent(
    label: String? = null,
    pinText: String = "",
    onPinTextChanged: (String) -> Unit = {},
    digitCount: Int = 6,
    error: String? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (label != null) {
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
                    if (it.length <= digitCount) onPinTextChanged(it)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                decorationBox = {
                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        repeat(digitCount) { index ->
                            DigitView(index, pinText)
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                    }
                })

            if (!error.isNullOrBlank()) {
                Spacer(Modifier.height(16.dp))
                Text(error, style = MaterialTheme.typography.titleSmall.copy(color = Color.Red))
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
    pinText: String
) {
    val modifier =
        Modifier
            .size(46.dp)
            .border(
                width = 1.dp,
                color = Color.Green,
                shape = RoundedCornerShape(8.dp)
            )

    Box(
        modifier = modifier
    ) {
        Text(
            text = if (index >= pinText.length) "" else pinText[index].toString(),
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}


