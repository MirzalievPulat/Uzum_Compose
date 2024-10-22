package uz.gita.uzumcompose.screens.auth.signUpVerify

import android.os.CountDownTimer
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.presentation.auth.signInVerify.SignInVerifyContract
import uz.gita.presentation.auth.signUpVerify.SignUpVerifyContract
import uz.gita.presentation.auth.signUpVerify.SignUpVerifyVM
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.components.AppBottomSheet
import uz.gita.uzumcompose.ui.components.AppTextButton
import uz.gita.uzumcompose.ui.components.NetworkErrorDialog
import uz.gita.uzumcompose.ui.components.PinViewComponent
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.utils.extensions.makeReadable


class SignUpVerifyScreen(private val phoneNumber: String) : Screen {
    @Composable
    override fun Content() {
        val viewModel: SignUpVerifyContract.ViewModel = getViewModel<SignUpVerifyVM>()

        SignUpVerifyContent(
            phoneNumber,
            viewModel.collectAsState(),
            viewModel::onEventDispatcher
        )
    }
}

@Preview
@Composable
fun SignUpVerifyPreview() {
    UzumComposeTheme {
        SignUpVerifyContent("", remember { mutableStateOf(SignUpVerifyContract.UIState()) }, {})
    }
}

//Phone number textfield
@Composable
fun SignUpVerifyContent(
    phoneNumber: String,
    uiState: State<SignUpVerifyContract.UIState>,
    onEventDispatcher: (SignUpVerifyContract.Intent) -> Unit
) {

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
    }

    var pinText by remember { mutableStateOf("") }
    var timeLeft by remember { mutableStateOf(60) }

    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    var isSheetVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(uiState.value.resendCode) {
        object : CountDownTimer(60_000, 1_000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                timeLeft = 0
            }
        }.start()
    }

    if (uiState.value.networkError) {
        NetworkErrorDialog(onDismissRequest = { onEventDispatcher(SignUpVerifyContract.Intent.DismissDialog) })
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier.background(color = Color.White)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clip(CircleShape)
                        .clickable {
                            onEventDispatcher.invoke(SignUpVerifyContract.Intent.SelectBack)
                        }
                        .padding(8.dp)
                )

                AppTextButton(text = stringResource(id = R.string.btn_sms_is_not_comming)) {
                    isSheetVisible = true
                }
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier.background(color = Color.White)
                .fillMaxSize()
                .padding(contentPadding)
        ) {

            //bottomsheet

            if (isSheetVisible) {
                AppBottomSheet(
                    header = stringResource(R.string.btn_sms_is_not_comming),
                    infoText = stringResource(R.string.txt_sms_not_coming_info),
                    context = LocalContext.current,
                    networkStatusValidator = uiState.value.networkStatusValidator!!,
                    onDismissRequest = { isSheetVisible = false },
                )
            }


            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 72.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.txt_sms_was_sent),
                    color = Color.BlackUzum,
                    fontFamily = fontFamilyUzum,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )

                Text(
                    text = phoneNumber.makeReadable(),
                    color = Color.BlackUzum,
                    fontFamily = fontFamilyUzum,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(44.dp))

                PinViewComponent(
                    digitCount = 6,
                    pinText = pinText,
                    onPinTextChanged = {
                        pinText = it

                        if (pinText.length == 6) {
                            onEventDispatcher.invoke(SignUpVerifyContract.Intent.GoToPin(pinText))
                        }
                    },
                    error = uiState.value.codeError,
                    focusRequester = focusRequester
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (uiState.value.showProgress) {
                    CircularProgressIndicator(
                        color = Color.HintUzum,
                        strokeCap = StrokeCap.Square,
                        strokeWidth = 4.dp,
                        modifier = Modifier.scale(0.5f)
                    )
                } else {
                    Spacer(modifier = Modifier.height(32.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (timeLeft == 0) {
                        AppTextButton(text = stringResource(R.string.txt_send_code_again)) {
                            onEventDispatcher.invoke(SignUpVerifyContract.Intent.SelectResend)
                        }
                    } else {
                        Text(
                            text = stringResource(R.string.txt_after_60_sec).replace(
                                oldValue = "60",
                                newValue = timeLeft.toString()
                            ),
                            color = Color.HintUzum,
                            fontFamily = fontFamilyUzum,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp,
                        )
                    }
                }
            }

        }
    }

}

