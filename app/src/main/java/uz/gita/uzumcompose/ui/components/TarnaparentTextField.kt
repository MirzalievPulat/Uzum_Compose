package uz.gita.uzumcompose.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.ui.theme.fontUzumPro

@Composable
fun TransparentTextField(
    modifier: Modifier = Modifier,
    hint: String,
    value: String,
//    errorText:String? = null,
    onValueChange: (String) -> Unit,
//    enabled :Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
//    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
) {
    BasicTextField(value = value, onValueChange = onValueChange,
        modifier = modifier.width(250.dp),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        maxLines = 1,
//        enabled = enabled,
        textStyle = TextStyle(
            fontFamily = fontUzumPro,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = Color.BlackUzum
        ),
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(Color.PinkUzum),
        decorationBox = { innerTextField ->
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .background(color = Color.TextField, shape = RoundedCornerShape(12.dp))
//                        .height(50.dp)
                        .defaultMinSize(minHeight = 50.dp),
//                        .padding(start = 12.dp, end = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    leadingIcon?.invoke()
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = if (leadingIcon != null) 8.dp else 0.dp)
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                fontFamily = fontUzumPro,
                                fontWeight = FontWeight.Medium,
                                fontSize = 18.sp,
                                color = Color.HintUzum
                            )

                        }
                        innerTextField()
                    }
                }


            }
        }
    )
}

@Preview
@Composable
fun PreviewTransparent() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {

            Spacer(modifier = Modifier.height(30.dp))

            TransparentTextField(
                hint = "(00) 000-00-00",
                value = "",
                onValueChange = {

                },
                leadingIcon = {
                    Text(
                        text = "+998",
                        style = TextStyle(
                            fontFamily = fontFamilyUzum,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                        ),
                    )
                },
            )
        }

    }

}