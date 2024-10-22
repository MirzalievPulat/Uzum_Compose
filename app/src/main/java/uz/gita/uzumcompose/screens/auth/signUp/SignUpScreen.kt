package uz.gita.uzumcompose.screens.auth.signUp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
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
import androidx.lifecycle.Lifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.common.other.GenderType
import uz.gita.presentation.auth.signIn.SignInContract
import uz.gita.presentation.auth.signUp.SignUpContract
import uz.gita.presentation.auth.signUp.SignUpVM
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.components.AppBottomSheet
import uz.gita.uzumcompose.ui.components.AppButton
import uz.gita.uzumcompose.ui.components.AppRadioButton
import uz.gita.uzumcompose.ui.components.AppTextButton
import uz.gita.uzumcompose.ui.components.AppTextField
import uz.gita.uzumcompose.ui.components.DatePickerModal
import uz.gita.uzumcompose.ui.components.NetworkErrorDialog
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.utils.extensions.showToast
import uz.gita.uzumcompose.utils.helper.MaskVisualTransformation
import uz.gita.uzumcompose.utils.helper.rememberImeState

class SignUpScreen : Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: SignUpContract.ViewModel = getViewModel<SignUpVM>()
        viewModel.collectSideEffect(lifecycleState = Lifecycle.State.RESUMED) {
            when (it) {
                is SignUpContract.SideEffect.Message -> {
                    context.showToast(it.message)
                }
            }
        }

        SignUpScreenContent(viewModel.collectAsState(), viewModel::onEventDispatcher)
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    UzumComposeTheme {
        SignUpScreenContent(remember {
            mutableStateOf(SignUpContract.UIState())
        }, {})
    }
}

@Composable
fun SignUpScreenContent(
    uiState: State<SignUpContract.UIState>,
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

    var isSheetVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val imeState = rememberImeState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.scrollTo(scrollState.maxValue)
        }
    }

    if (uiState.value.networkDialog){
        NetworkErrorDialog (onDismissRequest = {onEventDispatcher(SignUpContract.Intent.DialogDismiss)})
    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            Column(modifier = Modifier.background(Color.White)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    AppTextButton(
                        text = stringResource(R.string.btn_support),
                    ) {
                        isSheetVisible = true
                    }
                }
            }

        }
    ) { contentPadding ->

        //bottomsheet

        if (isSheetVisible) {
            AppBottomSheet(
                header = stringResource(R.string.btn_support),
                context = LocalContext.current,
                networkStatusValidator = uiState.value.networkStatusValidator!!,
                onDismissRequest = { isSheetVisible = false },
            )
        }


        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(contentPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 60.dp)
                .imePadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text(
                text = stringResource(R.string.txt_welcome),
                color = Color.BlackUzum,
                fontFamily = fontFamilyUzum,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            AppTextField(
                hint = stringResource(R.string.tf_phone_number),
                value = phone,
                onValueChange = {
                    println(it)
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
                errorText = uiState.value.phoneNumberError,
//                modifier = Modifier.focusRequester(focusRequester),
            )



            Spacer(modifier = Modifier.height(18.dp))

            var isVisible by remember { mutableStateOf(false) }
            val eyeIcon = if (isVisible) painterResource(id = R.drawable.open_eye)
            else painterResource(id = R.drawable.close_eye)

            AppTextField(
                hint = stringResource(R.string.tf_password),
                value = password,
                onValueChange = { if (it.length <= 26 && !it.contains(" ")) password = it },
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
                errorText = uiState.value.passwordError
            )


            Spacer(modifier = Modifier.height(18.dp))

            AppTextField(
                hint = stringResource(R.string.tf_first_name),
                value = firstName,
                onValueChange = { if (it.length <= 26 && !it.contains(" ")) firstName = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                errorText = uiState.value.firstNameError
            )


            Spacer(modifier = Modifier.height(18.dp))



            AppTextField(
                hint = stringResource(R.string.tf_last_name),
                value = lastName,
                onValueChange = { if (it.length <= 30 && !it.contains(" ")) lastName = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                errorText = uiState.value.lastNameError
            )


            Spacer(modifier = Modifier.height(18.dp))


            DatePickerModal(
                onDateSelected = { birthDate = it.toString() },
                errorText = uiState.value.birthDateError
            )

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
                showProgress = uiState.value.isLoading
            ) {
                onEventDispatcher.invoke(
                    SignUpContract.Intent.ClickContinue(
                        firstName,
                        lastName,
                        password,
                        phone,
                        birthDate,
                        if (isMale) GenderType.MALE else GenderType.FEMALE
                    )
                )
                println(listOf(firstName, lastName, password, phone, birthDate))
            }

            Spacer(modifier = Modifier.height(18.dp))

            AppTextButton(
                text = stringResource(R.string.btn_sign_in),
                modifier = Modifier.fillMaxWidth()
            ) {
                Log.d("TAG", "SignUpScreenContent: kirdi")
                onEventDispatcher.invoke(SignUpContract.Intent.SelectSignIn)
            }
        }

    }
}
