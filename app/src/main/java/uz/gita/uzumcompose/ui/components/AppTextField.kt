package uz.gita.uzumcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.WhiteBg
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    hint: String,
    value: String,
    errorText: String? = null,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
) {
    BasicTextField(value = value, onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        maxLines = 1,
        enabled = enabled,
        textStyle = TextStyle(
            fontFamily = fontFamilyUzum,
            fontWeight = FontWeight.Medium
        ),
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(Color.PinkUzum),
        decorationBox = { innerTextField ->
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.WhiteBg, shape = RoundedCornerShape(12.dp))
                        .defaultMinSize(minHeight = 50.dp)
                        .padding(start = 12.dp, end = 6.dp),
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
                                style = TextStyle(
                                    fontFamily = fontFamilyUzum,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.HintUzum,
                                    fontSize = 14.sp,
                                )
                            )
                        }
                        innerTextField()
                    }
                    trailingIcon?.invoke()
                }

                errorText?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorText,
                        fontFamily = fontFamilyUzum,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = Color.Red,
                        lineHeight = 16.sp,
                        modifier = Modifier.padding(start = 16.dp),
                    )
                }

            }
        }
    )
}

@Preview
@Composable
fun Preview() {
    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {

        Spacer(modifier = Modifier.height(30.dp))

        AppTextField(
            hint = "Phone",
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
            enabled = false,
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.close_eye),
                    contentDescription = "eye",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .clickable { }
                        .padding(8.dp)
                )
            },
            errorText = "Salom dunyo",
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .clickable {

            }
        )
    }


}