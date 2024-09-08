package uz.gita.uzumcompose.presentation.signUpVerify

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
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
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.presentation.signInVerify.SignInVerifyContract
import uz.gita.uzumcompose.ui.components.AppTextButton
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.ui.components.PinViewComponent
import kotlin.random.Random


class SignUpVerifyScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel:SignUpVerifyContract.ViewModel = getViewModel<SignUpVerifyVM>()

        UzumComposeTheme {
            SignUpVerifyContent(viewModel.collectAsState().value,viewModel::onEventDispatcher)
        }
    }
}

@Preview
@Composable
fun SignUpVerifyPreview() {
    UzumComposeTheme {
        SignUpVerifyContent(SignUpVerifyContract.UIState(),{})
    }
}

//Phone number textfield
@Composable
fun SignUpVerifyContent(
    uiState:SignUpVerifyContract.UIState,
    onEventDispatcher:(SignUpVerifyContract.Intent)->Unit
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
    var resendCode by remember { mutableStateOf(Random.nextFloat()) }

    val focusRequester by remember { mutableStateOf(FocusRequester()) }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(resendCode) {
        object : CountDownTimer(60_000, 1_000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                timeLeft = 0
            }
        }.start()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
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

                AppTextButton(text = "SMS is not coming") {

                }
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
                    text = "+998 92 333-33-33",
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
                    },
                    error = "",
                    focusRequester = focusRequester

                )

                Spacer(modifier = Modifier.height(48.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (timeLeft == 0) "Send code again"
                        else "You can send\nit again after $timeLeft sec",
                        color = if (timeLeft == 0) Color.PinkUzum else Color.HintUzum,
                        fontFamily = fontFamilyUzum,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        style = TextStyle(lineHeight = 16.sp),
                        modifier = Modifier.clickable(
                            enabled = timeLeft == 0,
                            onClick = {
                                resendCode = Random.nextFloat()

                            }
                        )
                    )
                }
            }

        }
    }

}

