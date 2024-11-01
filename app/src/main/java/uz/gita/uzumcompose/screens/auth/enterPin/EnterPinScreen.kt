package uz.gita.uzumcompose.screens.auth.enterPin

import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.presentation.auth.enterPin.EnterPinContract
import uz.gita.presentation.auth.enterPin.EnterPinVM
import uz.gita.presentation.auth.setPin.SetPinContract
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.components.DigitBuilder
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.DarkGreenUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum

class EnterPinScreen() : Screen {
    @Composable
    override fun Content() {
        val viewModel: EnterPinContract.ViewModel = getViewModel<EnterPinVM>()
        UzumComposeTheme {
            EnterPinScreenContent(viewModel.collectAsState(), viewModel::onEventDispatcher)
        }
    }
}

@Preview
@Composable
fun EnterPinScreenPreview() {
    UzumComposeTheme {
        EnterPinScreenContent(remember {
            mutableStateOf(EnterPinContract.UIState())
        }, {})
    }
}

@Composable
fun EnterPinScreenContent(
    uiState: State<EnterPinContract.UIState>,
    onEventDispatcher: (EnterPinContract.Intent) -> Unit
) {

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
    }

    val vibrator = LocalContext.current.getSystemService(VIBRATOR_SERVICE) as Vibrator

    val transition = remember { Animatable(0f) }

    //error animation
    LaunchedEffect(uiState.value.errorAnim) {
        if (uiState.value.errorAnim != 0L) {
            vibrator.cancel()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val effect = VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE)
                vibrator.vibrate(effect)
            } else {
                vibrator.vibrate(400)
            }

            for (i in 0..9) {
                when (i % 2) {
                    0 -> {
                        transition.animateTo(
                            9f,
                            animationSpec = tween(
                                durationMillis = 50,
                                easing = LinearEasing
                            )
                        )
                    }

                    else -> {
                        transition.animateTo(
                            -9f,
                            animationSpec = tween(
                                durationMillis = 50,
                                easing = LinearEasing
                            )
                        )
                    }
                }
            }
            transition.animateTo(0f)
        }

    }





    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)) {


        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = buildAnnotatedString {
                append(stringResource(R.string.hello))
                append(",\n")
                withStyle(style = SpanStyle(fontSize = 24.sp)) { append(uiState.value.name.uppercase()) }
            },
            color = Color.BlackUzum,
            fontFamily = fontFamilyUzum,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(x = transition.value.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in 0..3) {
                Spacer(modifier = Modifier.width(14.dp))
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(
                            color = if (uiState.value.currentCode.length == 4) {
                                if (uiState.value.fourDigitCorrect) Color.DarkGreenUzum else Color.Red
                            } else if (uiState.value.currentCode.length > i) Color.PinkUzum
                            else Color.HintUzum,
                            shape = CircleShape
                        )
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
        }


        Spacer(modifier = Modifier.height(48.dp))

        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.weight(1f))

            for (i in 1..3) {
                DigitBuilder(number = "$i") {
                    onEventDispatcher(EnterPinContract.Intent.ClickDigit("$i"))
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.weight(1f))

            for (i in 4..6) {
                DigitBuilder(number = "$i") {
                    onEventDispatcher(EnterPinContract.Intent.ClickDigit("$i"))
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.weight(1f))

            for (i in 7..9) {
                DigitBuilder(number = "$i") {
                    onEventDispatcher(EnterPinContract.Intent.ClickDigit("$i"))
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.Transparent, shape = CircleShape),
            )

            Spacer(modifier = Modifier.weight(1f))

            DigitBuilder(number = "0") {
                onEventDispatcher(EnterPinContract.Intent.ClickDigit("0"))
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.Transparent, shape = CircleShape)
                    .clickable(enabled = uiState.value.currentCode.isNotEmpty(),
                        onClick = {
                            onEventDispatcher(EnterPinContract.Intent.ClickDelete)
                        }),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Delete",
                    tint = if (uiState.value.currentCode.isNotEmpty()) Color.Gray else Color.Transparent
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(40.dp))
    }


}
