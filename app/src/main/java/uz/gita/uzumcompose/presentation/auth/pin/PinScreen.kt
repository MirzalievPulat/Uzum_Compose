package uz.gita.uzumcompose.presentation.auth.pin

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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
import uz.gita.uzumcompose.ui.components.DigitBuilder
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum

class PinScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: PinContract.ViewModel = getViewModel<PinVM>()

        UzumComposeTheme {
            PinScreenContent(viewModel.collectAsState().value, viewModel::onEventDispatcher)
        }
    }
}

@Preview
@Composable
fun PinScreenPreview() {
    UzumComposeTheme {
        PinScreenContent(PinContract.UIState(), {})
    }
}

@Composable
fun PinScreenContent(
    uiState: PinContract.UIState,
    onEventDispatcher: (PinContract.Intent) -> Unit
) {

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
    }

    var text by remember {
        mutableStateOf("")
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "",
                    tint = Color.Transparent,
                    modifier = Modifier
                        .clip(CircleShape)
                        .padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(72.dp))

            Text(
                text = stringResource(R.string.txt_set_pin),
                color = Color.BlackUzum,
                fontFamily = fontFamilyUzum,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                for (i in 0..3) {
                    Spacer(modifier = Modifier.width(14.dp))
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(
                                color = if (text.length == 4) Color.Green
                                else if (text.length > i) Color.PinkUzum
                                else Color.HintUzum,
                                shape = CircleShape
                            )
                    )
                }
            }

            //pin length check
            if (text.length == 4) {
                onEventDispatcher.invoke(PinContract.Intent.GoNextScreen(text))
            }


            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                Spacer(modifier = Modifier.weight(1f))

                for (i in 1..3) {
                    DigitBuilder(number = "$i") {
                        if (text.length < 4) text += "$i"
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                Spacer(modifier = Modifier.weight(1f))

                for (i in 4..6) {
                    DigitBuilder(number = "$i") {
                        if (text.length < 4) text += "$i"
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                Spacer(modifier = Modifier.weight(1f))

                for (i in 7..9) {
                    DigitBuilder(number = "$i") {
                        if (text.length < 4) text += "$i"
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
                    if (text.length < 4) text += "0"
                }

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.Transparent, shape = CircleShape)
                        .clickable(enabled = text.isNotEmpty(),
                            onClick = {
                                if (text.isNotBlank()) {
                                    text = text.substring(0, text.length - 1)
                                }
                            }),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Delete",
                        tint = if (text.isNotEmpty()) Color.Gray else Color.Transparent
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }

}
