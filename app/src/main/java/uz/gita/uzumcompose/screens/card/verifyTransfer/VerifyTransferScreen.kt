package uz.gita.uzumcompose.screens.card.verifyTransfer

import android.os.CountDownTimer
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.presentation.cards.transferFromTo.TransferFromToContract
import uz.gita.presentation.cards.verifyTransfer.VerifyTransferContract
import uz.gita.presentation.cards.verifyTransfer.VerifyTransferVM
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.components.AppTextButton
import uz.gita.uzumcompose.ui.components.NetworkErrorDialog
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.VeryPlainGray
import uz.gita.uzumcompose.ui.theme.WhiteBg
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.ui.theme.fontUzumPro

class VerifyTransferScreen : Screen {

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: VerifyTransferContract.ViewModel = getViewModel<VerifyTransferVM>()
        viewModel.collectSideEffect {
            if (it is VerifyTransferContract.SideEffect.ToastMessage) {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }

        VerifyTransferContent(viewModel.collectAsState(), viewModel::onEventDispatcher, viewModel.code)
    }
}

@Preview
@Composable
fun VerifyTransferPrev() {
    UzumComposeTheme {
        VerifyTransferContent(
            remember { mutableStateOf(VerifyTransferContract.UIState()) },
            {},
            remember { mutableStateOf("") }
        )
    }
}

@Composable
fun VerifyTransferContent(
    uiState: State<VerifyTransferContract.UIState>,
    onEventDispatcher: (VerifyTransferContract.Intent) -> Unit,
    code: MutableState<String>
) {

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
    }

    if (uiState.value.networkDialog) {
        NetworkErrorDialog { onEventDispatcher(VerifyTransferContract.Intent.NetworkDialogDismiss) }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 8.dp)
                    .background(color = Color.White),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back arrow",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(
                            onClick = {
                                onEventDispatcher(VerifyTransferContract.Intent.Back)
                            }
                        )
                        .padding(8.dp)
                )

            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = Color.White)
        ) {


            Text(
                text = stringResource(R.string.approve),
                color = Color.BlackUzum,
                fontSize = 32.sp,
                fontFamily = fontUzumPro,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            )


            Text(
                text = stringResource(R.string.enter_the_code)
                    .replace("#", uiState.value.phoneNumber),
                color = Color.BlackUzum,
                fontSize = 16.sp,
                fontFamily = fontUzumPro,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 32.dp)
            )

            Text(
                text = stringResource(R.string.enter_code),
                color = Color.BlackUzum,
                fontSize = 14.sp,
                fontFamily = fontUzumPro,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            )

            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }

            val focusManager = LocalFocusManager.current
            if (code.value.length == 6){
                focusManager.clearFocus()
            }

            OutlinedTextField(
                value = code.value,
                onValueChange = {
                    onEventDispatcher(VerifyTransferContract.Intent.CodeChange(it))
                },
                textStyle = TextStyle(
                    color = Color.BlackUzum,
                    fontSize = 18.sp,
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.Medium,
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.WhiteBg,
                    focusedIndicatorColor = Color.PinkUzum,
                    unfocusedIndicatorColor = Color.VeryPlainGray,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 4.dp)
                    .focusRequester(focusRequester)
            )

            var timeLeft by remember { mutableIntStateOf(60) }
            LaunchedEffect(uiState.value.sendAgain) {
                object : CountDownTimer(60_000, 1_000) {
                    override fun onTick(millisUntilFinished: Long) {
                        timeLeft = (millisUntilFinished / 1000).toInt()
                    }

                    override fun onFinish() {
                        timeLeft = 0
                    }
                }.start()
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (timeLeft == 0) {
                    AppTextButton(
                        text = stringResource(R.string.txt_send_code_again),
                        zeroPadding = true
                    ) {
                        onEventDispatcher.invoke(VerifyTransferContract.Intent.SendAgainClick)
                    }
                } else {
                    Text(
                        text = stringResource(R.string.send_code_60).replace(
                            oldValue = "60",
                            newValue = timeLeft.toString()
                        ),
                        color = Color.HintUzum,
                        fontFamily = fontFamilyUzum,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        style = TextStyle(lineHeight = 16.sp),
                    )
                }
            }
        }
    }
}