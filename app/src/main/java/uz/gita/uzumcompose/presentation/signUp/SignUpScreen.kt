package uz.gita.uzumcompose.presentation.signUp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.components.AppBottomSheet
import uz.gita.uzumcompose.ui.components.AppButton
import uz.gita.uzumcompose.ui.components.AppRadioButton
import uz.gita.uzumcompose.ui.components.AppTextButton
import uz.gita.uzumcompose.ui.components.AppTextField
import uz.gita.uzumcompose.ui.components.DatePickerModal
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.utils.helper.MaskVisualTransformation
import uz.gita.uzumcompose.utils.helper.rememberImeState

class SignUpScreen : Screen {
    @Composable
    override fun Content() {
        UzumComposeTheme {
            val viewModel: SignUpContract.ViewModel = getViewModel<SignUpVM>()

            SignUpScreenContent(viewModel.collectAsState().value, viewModel::onEventDispatcher)

        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    UzumComposeTheme {
        SignUpScreenContent(SignUpContract.UIState(), {})
    }
}

//Phone number textfield
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreenContent(
    uiState: SignUpContract.UIState,
    onEventDispatcher: (SignUpContract.Intent) -> Unit
) {

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
    }

    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("0") }
    var isMale by remember { mutableStateOf(true) }

    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    var isSheetVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val imeState = rememberImeState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value){
            scrollState.scrollTo(scrollState.maxValue)
        }
    }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState).imePadding()
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                AppTextButton(
                    text = stringResource(R.string.btn_support),
                ) {
                    isSheetVisible = true
                }
            }
            //bottomsheet

            if (isSheetVisible) {
                AppBottomSheet(
                    header = stringResource(R.string.btn_support),
                    context = LocalContext.current,
                    onDismissRequest = { isSheetVisible = false },
                )
            }



            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.txt_welcome),
                    color = Color.BlackUzum,
                    fontFamily = fontFamilyUzum,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(18.dp))


                AppTextField(
                    hint = stringResource(R.string.tf_phone_number),
                    value = phone,
                    onValueChange = {
                        if (it.length <= 9 && (it.isDigitsOnly() || it == "")) phone = it
                    },
                    visualTransformation = MaskVisualTransformation(mask = "## ###-##-##"),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
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
                    modifier = Modifier.focusRequester(focusRequester),
                )



                Spacer(modifier = Modifier.height(18.dp))

                var isVisible by remember { mutableStateOf(false) }
                val eyeIcon = if (isVisible) painterResource(id = R.drawable.open_eye)
                else painterResource(id = R.drawable.close_eye)

                AppTextField(
                    hint = stringResource(R.string.tf_password),
                    value = password,
                    onValueChange = { if (it.length <= 30) password = it },
                    trailingIcon = {
                        Icon(
                            painter = eyeIcon,
                            contentDescription = "eye",
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .clickable { isVisible = !isVisible }
                                .padding(8.dp)
                        )
                    },
                    visualTransformation = if (isVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                )


                Spacer(modifier = Modifier.height(18.dp))

                AppTextField(
                    hint = stringResource(R.string.tf_first_name),
                    value = firstName,
                    onValueChange = { if (it.length <= 30) firstName = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )


                Spacer(modifier = Modifier.height(18.dp))



                AppTextField(
                    hint = stringResource(R.string.tf_last_name),
                    value = lastName,
                    onValueChange = { if (it.length <= 30) lastName = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )


                Spacer(modifier = Modifier.height(18.dp))



                DatePickerModal {
                    birthDate  = it.toString()
                }


                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    AppRadioButton(
                        selected = isMale,
                        onClick = { isMale = true },
                        text = stringResource(R.string.txt_male)
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    AppRadioButton(
                        selected = !isMale,
                        onClick = { isMale = false },
                        text = stringResource(R.string.txt_female)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                AppButton(
                    text = stringResource(R.string.btn_continue),
                    modifier = Modifier.fillMaxWidth(),
                    showProgress = uiState.isLoading
                ) {
                    onEventDispatcher.invoke(
                        SignUpContract.Intent.ClickContinue(
                            firstName,
                            lastName,
                            password,
                            "+998"+phone,
                            birthDate,
                            if (isMale) 0 else 1
                        )
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                AppTextButton(
                    text = stringResource(R.string.btn_sign_in),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    onEventDispatcher.invoke(SignUpContract.Intent.SelectSignIn)
                }
            }
        }
    }
}
