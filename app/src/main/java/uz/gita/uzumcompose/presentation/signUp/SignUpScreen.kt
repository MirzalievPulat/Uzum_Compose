package uz.gita.uzumcompose.presentation.signUp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.TextField
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.utils.composable.MaskVisualTransformation


class SignUpScreen : Screen {
    @Composable
    override fun Content() {
        SignUpScreenContent()
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    UzumComposeTheme {
        SignUpScreenContent()
    }
}

//Phone number textfield
@Composable
fun SignUpScreenContent() {
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var isMale by remember { mutableStateOf(true) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Support",
                color = Color.PinkUzum,
                fontFamily = uz.gita.uzumcompose.presentation.sms.fontFamilyUzum,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp)
            )

//            Spacer(modifier = Modifier.height(72.dp))





            Column(modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(
                    text = "Welcome \nto Uzum Bank",
                    color = Color.BlackUzum,
                    fontFamily = uz.gita.uzumcompose.presentation.sms.fontFamilyUzum,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp)
                ) {
                    TextField(
                        value = phone,
                        onValueChange = { it ->
                            if (it.length <= 9) phone = it
                        },
                        textStyle = TextStyle(
                            fontFamily = uz.gita.uzumcompose.presentation.sms.fontFamilyUzum,
                            fontWeight = FontWeight.Medium
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        placeholder = {
                            Text(
                                text = "Phone number",
                                fontFamily = uz.gita.uzumcompose.presentation.sms.fontFamilyUzum,
                                fontWeight = FontWeight.Medium,
                                color = Color.HintUzum,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .padding(0.dp)
                                    .height(40.dp)
                            )
                        },
                        prefix = {
                            Text(
                                text = "+998",
                                fontFamily = uz.gita.uzumcompose.presentation.sms.fontFamilyUzum,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.TextField,
                            disabledContainerColor = Color.TextField,
                            errorContainerColor = Color.TextField,
                            unfocusedContainerColor = Color.TextField,
                            focusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            cursorColor = Color.PinkUzum,
                        ),
                        visualTransformation = MaskVisualTransformation("## ###-##-##"),
                        modifier = Modifier
                            .height(50.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                var isVisible by remember { mutableStateOf(false) }
                val eyeIcon = if (isVisible) painterResource(id = R.drawable.open_eye)
                else painterResource(id = R.drawable.close_eye)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp)
                ) {
                    TextField(
                        value = password,
                        onValueChange = {
                            if (it.length <= 30) password = it
                        },
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Password",
                                fontFamily = uz.gita.uzumcompose.presentation.sms.fontFamilyUzum,
                                fontWeight = FontWeight.Medium,
                                color = Color.HintUzum,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .padding(0.dp)
                                    .height(40.dp)
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            cursorColor = Color.PinkUzum,
                            focusedContainerColor = Color.TextField,
                            disabledContainerColor = Color.TextField,
                            errorContainerColor = Color.TextField,
                            unfocusedContainerColor = Color.TextField,
                            focusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ),
                        textStyle = TextStyle(
                            fontFamily = uz.gita.uzumcompose.presentation.sms.fontFamilyUzum,
                            fontWeight = FontWeight.Medium
                        ),
                        trailingIcon = {
                            IconButton(onClick = { isVisible = !isVisible }) {
                                Icon(
                                    painter = eyeIcon,
                                    contentDescription = "eye",
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        },
                        visualTransformation = if (isVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .height(50.dp)
                            .clip(RoundedCornerShape(12.dp)),
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp)
                ) {
                    TextField(
                        value = firstName,
                        onValueChange = {
                            if (firstName.length <= 30) firstName = it
                        },
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "First name",
                                fontFamily = uz.gita.uzumcompose.presentation.sms.fontFamilyUzum,
                                fontWeight = FontWeight.Medium,
                                color = Color.HintUzum,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .padding(0.dp)
                                    .height(40.dp)
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            cursorColor = Color.PinkUzum,
                            focusedContainerColor = Color.TextField,
                            disabledContainerColor = Color.TextField,
                            errorContainerColor = Color.TextField,
                            unfocusedContainerColor = Color.TextField,
                            focusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ),
                        textStyle = TextStyle(
                            fontFamily = uz.gita.uzumcompose.presentation.sms.fontFamilyUzum,
                            fontWeight = FontWeight.Medium
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .height(50.dp)
                            .clip(RoundedCornerShape(12.dp)),
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp)
                ) {
                    TextField(
                        value = lastName,
                        onValueChange = {
                            if (lastName.length <= 30) lastName = it
                        },
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Last name",
                                fontFamily = uz.gita.uzumcompose.presentation.sms.fontFamilyUzum,
                                fontWeight = FontWeight.Medium,
                                color = Color.HintUzum,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .padding(0.dp)
                                    .height(40.dp)
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            cursorColor = Color.PinkUzum,
                            focusedContainerColor = Color.TextField,
                            disabledContainerColor = Color.TextField,
                            errorContainerColor = Color.TextField,
                            unfocusedContainerColor = Color.TextField,
                            focusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ),
                        textStyle = TextStyle(
                            fontFamily = uz.gita.uzumcompose.presentation.sms.fontFamilyUzum,
                            fontWeight = FontWeight.Medium
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .height(50.dp)
                            .clip(RoundedCornerShape(12.dp)),
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp)
                ) {
                    TextField(
                        value = birthDate,
                        enabled = false,
                        onValueChange = {
                            if (birthDate.length <= 30) birthDate = it
                        },
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Date of birth",
                                fontFamily = uz.gita.uzumcompose.presentation.sms.fontFamilyUzum,
                                fontWeight = FontWeight.Medium,
                                color = Color.HintUzum,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .padding(0.dp)
                                    .height(40.dp)
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.TextField,
                            disabledContainerColor = Color.TextField,
                            errorContainerColor = Color.TextField,
                            unfocusedContainerColor = Color.TextField,
                            cursorColor = Color.PinkUzum,
                            focusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ),
                        textStyle = TextStyle(
                            fontFamily = uz.gita.uzumcompose.presentation.sms.fontFamilyUzum,
                            fontWeight = FontWeight.Medium
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier
                            .height(50.dp)
                            .clip(RoundedCornerShape(12.dp)),
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp)
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { isMale = true }
                            .padding(end = 16.dp)
                    ) {
                        RadioButton(
                            selected = isMale,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.BlackUzum,
                                unselectedColor = Color.BlackUzum,
                            ),
                            onClick = { isMale = true},
                            modifier = Modifier.scale(0.9f)
                        )

                        Text(text = "Male",
                            fontFamily = uz.gita.uzumcompose.presentation.sms.fontFamilyUzum,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Color.BlackUzum
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { isMale = false }
                            .padding(end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = !isMale,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.BlackUzum,
                                unselectedColor = Color.BlackUzum,
                            ),
                            onClick = {isMale = false},
                            modifier = Modifier.scale(0.9f)
                        )

                        Text(text = "Female",
                            fontFamily = uz.gita.uzumcompose.presentation.sms.fontFamilyUzum,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Color.BlackUzum
                        )
                    }
                }



                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp)
                ) {
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors().copy(containerColor = Color.PinkUzum),
                        onClick = { }) {
                        Text(
                            text = "Continue",
                            fontFamily = uz.gita.uzumcompose.presentation.sms.fontFamilyUzum,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sign in",
                        color = Color.PinkUzum,
                        fontFamily = uz.gita.uzumcompose.presentation.sms.fontFamilyUzum,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                    )
                }
            }

        }
    }

}

val fontFamilyUzum = FontFamily(
    Font(R.font.tt_uzum_medium, FontWeight.Medium),
    Font(R.font.tt_uzum_demibold, FontWeight.SemiBold),
    Font(R.font.tt_uzum_bold, FontWeight.Bold),
)
