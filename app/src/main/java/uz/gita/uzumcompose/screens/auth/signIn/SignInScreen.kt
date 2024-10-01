package uz.gita.uzumcompose.screens.auth.signIn

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.presentation.auth.signIn.SignInContract
import uz.gita.presentation.auth.signIn.SignInVM
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.components.AppBottomSheet
import uz.gita.uzumcompose.ui.components.AppButton
import uz.gita.uzumcompose.ui.components.AppTextButton
import uz.gita.uzumcompose.ui.components.AppTextField
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.utils.extensions.showToast
import uz.gita.uzumcompose.utils.helper.MaskVisualTransformation


class SignInScreen : Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: SignInContract.ViewModel = getViewModel<SignInVM>()
        viewModel.collectSideEffect {
            when(it){
                is SignInContract.SideEffect.Message->{
                    context.showToast(it.message)
                }
            }
        }

        SignInScreenContent(viewModel.collectAsState(), viewModel::onEventDispatcher)

    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    UzumComposeTheme {
        SignInScreenContent(remember { mutableStateOf(SignInContract.UIState()) }, {})
    }
}

//Phone number textfield
@Composable
fun SignInScreenContent(
    uiState: State<SignInContract.UIState>,
    onEventDispatcher: (SignInContract.Intent) -> Unit
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

    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    var isSheetVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier.background(color = Color.White),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.weight(1f))
                AppTextButton(
                    text = stringResource(R.string.btn_support),
                ) { isSheetVisible = true }
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .background(color = Color.White)
        ) {

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
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp)
                    .padding(top = 72.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
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
                            fontFamily = fontFamilyUzum,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                        )
                    },
                    modifier = Modifier.focusRequester(focusRequester),
                    errorText = uiState.value.phoneNumberError
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
                                .size(22.dp)
                                .padding(2.dp)
                                .clickable { isVisible = !isVisible }
                        )
                    },
                    visualTransformation = if (isVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    errorText = uiState.value.passwordError
                )


                Spacer(modifier = Modifier.height(18.dp))


                AppButton(
                    text = stringResource(R.string.btn_continue),
                    modifier = Modifier.fillMaxWidth(),
                    showProgress = uiState.value.isLoading
                ) {
                    onEventDispatcher.invoke(
                        SignInContract.Intent.ClickContinue(
                            password = password,
                            phone = phone,
                        )
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                AppTextButton(
                    text = stringResource(R.string.btn_sign_up),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    onEventDispatcher.invoke(SignInContract.Intent.SelectSignUp)
                }
            }
        }
    }

}
