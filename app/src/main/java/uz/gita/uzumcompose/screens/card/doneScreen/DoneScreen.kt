package uz.gita.uzumcompose.screens.card.doneScreen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.presentation.cards.doneTransfer.DoneTransferContract
import uz.gita.presentation.cards.doneTransfer.DoneTransferVM
import uz.gita.presentation.cards.transferFromTo.TransferFromToContract
import uz.gita.presentation.cards.transferFromTo.TransferFromToVM
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.screens.card.transferFromTo.TransferFromToContent
import uz.gita.uzumcompose.screens.main.MainScreen
import uz.gita.uzumcompose.screens.pages.home.BillBoardSection
import uz.gita.uzumcompose.ui.components.AppButton
import uz.gita.uzumcompose.ui.theme.DoneBg
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.PlainGreenUzum
import uz.gita.uzumcompose.ui.theme.TransparentGray2
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.ui.theme.fontUzumPro
import uz.gita.uzumcompose.utils.extensions.formatToMoney

class DoneTransferScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: DoneTransferContract.ViewModel = getViewModel<DoneTransferVM>()

        DoneTransferScreenContent(viewModel.collectAsState(), viewModel::onEventDispatcher)
    }
}

@Preview
@Composable
fun DoneTransferScreenPrev() {
    UzumComposeTheme {
        DoneTransferScreenContent(
            remember { mutableStateOf(DoneTransferContract.UIState()) },
            {},
        )
    }
}

@Composable
fun DoneTransferScreenContent(
    uiState: State<DoneTransferContract.UIState>,
    onEventDispatcher: (DoneTransferContract.Intent) -> Unit,
) {

    val navigator = LocalNavigator.current

    BackHandler {
        onEventDispatcher(DoneTransferContract.Intent.CloseClick)
    }

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.DoneBg,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(
            color = Color.DoneBg,
        )
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DoneBg)
    ) {
        Text(
            text = stringResource(R.string.done),
            color = Color.PlainGreenUzum,
            fontSize = 30.sp,
            fontFamily = fontFamilyUzum,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
//            letterSpacing = 2.sp,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 56.dp)
        )

        Text(
            text = stringResource(R.string.transferred_sum_to_card)
                .replace("#", formatToMoney(uiState.value.sum))
                .replace("$", uiState.value.pan),
            color = Color.White,
            fontSize = 30.sp,
            fontFamily = fontFamilyUzum,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 38.sp,
//            letterSpacing = 2.sp,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 40.dp)
        )

        BillBoardSection()

        Spacer(modifier = Modifier.weight(1f))


        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .width(80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.TransparentGray2,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .size(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_doc),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    text = stringResource(R.string.documents_and_details),
                    color = Color.White,
                    fontSize = 13.sp,
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .width(80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.TransparentGray2,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .size(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_star_checked),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    text = stringResource(R.string.add_to_favourite),
                    color = Color.White,
                    fontSize = 13.sp,
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }

        AppButton(
            text = stringResource(id = R.string.close),
            containerColor = Color.TransparentGray2,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp, bottom = 16.dp)
                .fillMaxWidth(),
        ) {
            onEventDispatcher(DoneTransferContract.Intent.CloseClick)
        }
    }
}