package uz.gita.uzumcompose.screens.card.transferCard

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
import uz.gita.common.other.CardsType
import uz.gita.presentation.cards.transfer.TransferContract
import uz.gita.presentation.cards.transfer.TransferVM
import uz.gita.presentation.helper.extensions.toDate
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.components.AppTextField
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.WhiteBg
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.ui.theme.fontUzumPro
import uz.gita.uzumcompose.utils.helper.MaskVisualTransformation

class TransferScreen : Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: TransferContract.ViewModel = getViewModel<TransferVM>()
        viewModel.collectSideEffect {
            if (it is TransferContract.SideEffect.ToastMessage) {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        TransferContent(viewModel.collectAsState(), viewModel::onEventDispatcher)
    }
}

@Preview
@Composable
fun TransferPrev() {
    UzumComposeTheme {
        TransferContent(
            remember { mutableStateOf(TransferContract.UIState()) },
            {}
        )
    }
}

@Composable
fun TransferContent(
    uiState: State<TransferContract.UIState>,
    onEventDispatcher: (TransferContract.Intent) -> Unit
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
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
//                        .size(36.dp)
                        .clip(CircleShape)
                        .clickable(
                            onClick = {
                                onEventDispatcher(TransferContract.Intent.Back)
                            }
                        )
                        .padding(8.dp)
                )

                Text(
                    text = stringResource(R.string.transfer_to_card),
                    color = Color.BlackUzum,
                    fontSize = 18.sp,
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 8.dp)
                )

            }
        }
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = Color.White)
//                .verticalScroll(rememberScrollState())
        ) {

            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(key1 = Unit) {
                focusRequester.requestFocus()
            }
            AppTextField(
                hint = stringResource(id = R.string.card_number),
                value = uiState.value.panText,
                onValueChange = { onEventDispatcher(TransferContract.Intent.CardNumberChange(it)) },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                    .height(56.dp)
                    .focusRequester(focusRequester),
                errorText = uiState.value.errorPan,
                trailingIcon = {
                    if (uiState.value.panText.isNotBlank()) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_close),
                            contentDescription = "Card scan",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clickable {
                                    onEventDispatcher(TransferContract.Intent.PanClearClick)
                                }
                        )
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_24_scan_card),
                            contentDescription = "Card scan",
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable {
                                    onEventDispatcher(TransferContract.Intent.CameraClick)
                                }
                                .padding(8.dp)
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = MaskVisualTransformation("#### #### #### ####")
            )

            if (uiState.value.cardsType != CardsType.BY_SEARCH){
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp, bottom = 16.dp)
                        .background(color = Color.WhiteBg, shape = RoundedCornerShape(12.dp))
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 3.dp, bottom = 3.dp, start = 3.dp)
                            .fillMaxHeight()
                            .weight(1f)
                            .background(
                                color = if (uiState.value.cardsType == CardsType.RECENT) Color.White else Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                onEventDispatcher(TransferContract.Intent.CardTypesChange(CardsType.RECENT))
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.recent),
                            fontFamily = fontFamilyUzum,
                            fontWeight = FontWeight.Medium,
                            color = Color.BlackUzum,
                            fontSize = 14.sp,
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(top = 3.dp, bottom = 3.dp, end = 3.dp)
                            .fillMaxHeight()
                            .weight(1f)
                            .background(
                                color = if (uiState.value.cardsType == CardsType.MY_CARDS) Color.White else Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                onEventDispatcher(TransferContract.Intent.CardTypesChange(CardsType.MY_CARDS))
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.my_cards),
                            fontFamily = fontFamilyUzum,
                            fontWeight = FontWeight.Medium,
                            color = Color.BlackUzum,
                            fontSize = 14.sp,
                        )
                    }
                }
            }

            if (uiState.value.lastTransferredCards.isEmpty()
                && uiState.value.myCards.isEmpty()
                && uiState.value.cardsType == CardsType.BY_SEARCH
                ){
                if (uiState.value.foundCard == null){
                    Text(
                        text = stringResource(R.string.no_tansfer_before),
                        fontFamily = fontFamilyUzum,
                        fontWeight = FontWeight.Medium,
                        color = Color.HintUzum,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }else{
                    Row(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .height(68.dp)
                            .fillMaxWidth()
                            .clickable {
                                onEventDispatcher(
                                    TransferContract.Intent.CardClick(
                                        name = uiState.value.foundCard!!,
                                        pan = uiState.value.panText
                                    )
                                )
                            }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_card_humo2),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(40.dp)
                                .aspectRatio(1.4f / 1f)
                                .clip(RoundedCornerShape(6.dp))
                        )

                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxHeight()
                                .weight(1f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = uiState.value.foundCard.toString(),
                                fontFamily = fontFamilyUzum,
                                fontWeight = FontWeight.Medium,
                                color = Color.BlackUzum,
                                fontSize = 16.sp,
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.ic_right_round_arrow),
                            contentDescription = null,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }

            }else{
                LazyColumn(
                    modifier = Modifier.wrapContentHeight(),
                ) {
                    if(uiState.value.cardsType == CardsType.RECENT ||
                        uiState.value.cardsType == CardsType.BY_SEARCH){

                        if (uiState.value.cardsType != CardsType.BY_SEARCH && uiState.value.lastTransferredCards.isEmpty()){
                            item{
                                Text(
                                    text = stringResource(R.string.no_recent_transactions),
                                    fontFamily = fontFamilyUzum,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.HintUzum,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 32.dp)
                                )
                            }
                        }else{
                            items(uiState.value.lastTransferredCards
                                .also { Log.d("TAG", "TransferCardContent: recent: $it") })
                            {transferredCard ->
                                Row(
                                    modifier = Modifier
                                        .padding(vertical = 4.dp)
                                        .height(68.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            onEventDispatcher(
                                                TransferContract.Intent.CardClick(
                                                    transferredCard.name,
                                                    transferredCard.pan
                                                )
                                            )
                                        }
                                        .padding(horizontal = 16.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_card_humo2),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(40.dp)
                                            .aspectRatio(1.4f / 1f)
                                            .clip(RoundedCornerShape(6.dp))
                                    )

                                    Column(
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp)
                                            .fillMaxHeight()
                                            .weight(1f)
                                    ) {
                                        Text(
                                            text = transferredCard.name,
                                            fontFamily = fontFamilyUzum,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.BlackUzum,
                                            fontSize = 16.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )

                                        Text(
                                            text = ("···· "+transferredCard.pan.takeLast(4)),
                                            fontFamily = fontFamilyUzum,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.HintUzum,
                                            fontSize = 16.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                    Box(modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(end = 16.dp),
                                        contentAlignment = Alignment.BottomCenter
                                    ){
                                        Text(
                                            text = transferredCard.time.toString().toDate(),
                                            fontFamily = fontFamilyUzum,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.HintUzum,
                                            fontSize = 12.sp,
                                            lineHeight = 14.sp
                                        )
                                    }


                                    Image(
                                        painter = painterResource(id = R.drawable.ic_right_round_arrow),
                                        contentDescription = null,
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                            }
                        }

                    }
                    if (uiState.value.cardsType == CardsType.MY_CARDS ||
                        uiState.value.cardsType == CardsType.BY_SEARCH){

                        if (uiState.value.cardsType != CardsType.BY_SEARCH && uiState.value.myCards.isEmpty()){
                            item{
                                Text(
                                    text = stringResource(R.string.no_cards),
                                    fontFamily = fontFamilyUzum,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.HintUzum,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 32.dp)
                                )
                            }
                        }else{
                            items(uiState.value.myCards.also { Log.d("TAG", "TransferCardContent: mycards: $it") }){ cardInfo ->
                                Row(
                                    modifier = Modifier
                                        .padding(vertical = 4.dp)
                                        .height(68.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            onEventDispatcher(TransferContract.Intent.CardClick(cardInfo.owner, cardInfo.pan))
                                        }
                                        .padding(horizontal = 16.dp, vertical = 12.dp)
                                    ,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_card_humo2),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(40.dp)
                                            .aspectRatio(1.4f / 1f)
                                            .clip(RoundedCornerShape(6.dp))
                                    )

                                    Column(
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp)
                                            .fillMaxHeight()
                                            .weight(1f)
                                    ) {
                                        Text(
                                            text = cardInfo.amount+" sum",
                                            fontFamily = fontFamilyUzum,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.BlackUzum,
                                            fontSize = 16.sp,
                                        )

                                        Text(
                                            text = cardInfo.pan + " · Aloqa Bank",
                                            fontFamily = fontFamilyUzum,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.HintUzum,
                                            fontSize = 16.sp,
                                        )
                                    }

                                    Image(
                                        painter = painterResource(id = R.drawable.ic_right_round_arrow),
                                        contentDescription = null,
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                            }
                        }


                    }

                }
            }


        }

    }

}