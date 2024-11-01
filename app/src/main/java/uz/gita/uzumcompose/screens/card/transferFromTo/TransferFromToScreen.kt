package uz.gita.uzumcompose.screens.card.transferFromTo

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.presentation.cards.transferFromTo.TransferFromToContract
import uz.gita.presentation.cards.transferFromTo.TransferFromToVM
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.components.AppButton
import uz.gita.uzumcompose.ui.components.AppTextField
import uz.gita.uzumcompose.ui.components.NetworkErrorDialog
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.TextField
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.WhiteBg
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.utils.extensions.formatToMoney
import uz.gita.uzumcompose.utils.extensions.toPrivatePan
import uz.gita.uzumcompose.utils.helper.AmountVisual

class TransferFromToScreen(val name: String, val pan: String) : Screen {

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: TransferFromToContract.ViewModel = getViewModel<TransferFromToVM>()
        viewModel.setToCard(Pair(name, pan))
        viewModel.collectSideEffect {
            if (it is TransferFromToContract.SideEffect.ToastMessage) {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }

        TransferFromToContent(viewModel.collectAsState(), viewModel::onEventDispatcher, viewModel.sum)
    }
}

@Preview
@Composable
fun TransferFromToPrev() {
    UzumComposeTheme {
        TransferFromToContent(
            remember { mutableStateOf(TransferFromToContract.UIState()) },
            {},
            remember { mutableStateOf("") }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferFromToContent(
    uiState: State<TransferFromToContract.UIState>,
    onEventDispatcher: (TransferFromToContract.Intent) -> Unit,
    sum: MutableState<String>
) {


    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
    }

    if (uiState.value.networkDialog) {
        NetworkErrorDialog { onEventDispatcher(TransferFromToContract.Intent.NetworkDialogDismiss) }
    }

    if (uiState.value.cardsBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onEventDispatcher(TransferFromToContract.Intent.BottomSheetClose) },
            containerColor = Color.White,
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true
            ),
            dragHandle = {
                Box(modifier = Modifier
                    .padding(bottom = 24.dp, top = 12.dp)
                    .size(40.dp, 5.dp)
                    .background(color = Color.LightGray, shape = CircleShape))
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.cards),
                    color = Color.BlackUzum,
                    fontSize = 18.sp,
                    fontFamily = fontFamilyUzum,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 8.dp)
                )

                LazyColumn {
                    items(uiState.value.myCards) { cardInfo ->
                        Row(
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .height(68.dp)
                                .fillMaxWidth()
                                .clickable {
                                    onEventDispatcher(TransferFromToContract.Intent.CardChoose(cardInfo))
                                }
                                .padding(vertical = 12.dp, horizontal = 16.dp),
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
                                    text = cardInfo.amount + " sum",
                                    fontFamily = fontFamilyUzum,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.BlackUzum,
                                    fontSize = 16.sp,
                                )

                                Text(
                                    text = cardInfo.pan.takeLast(4) + " · Aloqa Bank",
                                    fontFamily = fontFamilyUzum,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.HintUzum,
                                    fontSize = 14.sp,
                                )
                            }

                            if (uiState.value.fromCard == cardInfo) {
                                Image(
                                    painter = painterResource(id = R.drawable.check_24px),
                                    contentDescription = null,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                        }
                    }
                }


                Row(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .height(68.dp)
                        .fillMaxWidth()
                        .clickable {
                            onEventDispatcher(TransferFromToContract.Intent.AddCardClick)
                        }
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .aspectRatio(1.4f / 1f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(color = Color.WhiteBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = null
                        )
                    }

                    Text(
                        text = stringResource(R.string.add_card),
                        fontFamily = fontFamilyUzum,
                        fontWeight = FontWeight.Medium,
                        color = Color.BlackUzum,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
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
                                onEventDispatcher(TransferFromToContract.Intent.Back)
                            }
                        )
                        .padding(8.dp)
                )

                Text(
                    text = stringResource(R.string.txt_transfer),
                    color = Color.BlackUzum,
                    fontSize = 18.sp,
                    fontFamily = fontFamilyUzum,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 8.dp)
                )

            }
        },
        floatingActionButton = {
            AppButton(
                text = stringResource(R.string.txt_transfer),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textStyle = TextStyle(fontSize = 16.sp),
                showProgress = uiState.value.transferLoading,
                onClick = {
                    onEventDispatcher(TransferFromToContract.Intent.TransferClick)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = Color.White)
        ) {

            Box(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                ) {
                    Box(modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 4.dp)
                        .fillMaxWidth()
                        .background(color = Color.WhiteBg, shape = RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            onEventDispatcher(TransferFromToContract.Intent.FromCardChoose)
                        }
                        .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .height(68.dp)
                                .fillMaxWidth()
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
                                    text = formatToMoney(uiState.value.fromCard.amount) + " sum",
                                    fontFamily = fontFamilyUzum,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.BlackUzum,
                                    fontSize = 16.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )

                                Text(
                                    text = uiState.value.fromCard.pan + " · Aloqa Bank",
                                    fontFamily = fontFamilyUzum,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.HintUzum,
                                    fontSize = 14.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                            }

                            Icon(
                                painter = painterResource(id = R.drawable.ic_down_arrow),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Box(modifier = Modifier
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp, top = 4.dp)
                        .fillMaxWidth()
                        .background(color = Color.WhiteBg, shape = RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            onEventDispatcher(TransferFromToContract.Intent.Back)
                        }
                        .padding(14.dp)

                    ) {
                        Row(
                            modifier = Modifier
                                .height(68.dp)
                                .fillMaxWidth()
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
                                    text = uiState.value.toCard.first,
                                    fontFamily = fontFamilyUzum,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.BlackUzum,
                                    fontSize = 16.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )

                                Text(
                                    text = uiState.value.toCard.second.toPrivatePan(),
                                    fontFamily = fontFamilyUzum,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.HintUzum,
                                    fontSize = 14.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                            }

                            Icon(
                                painter = painterResource(id = R.drawable.ic_down_arrow),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }


                }

                Image(
                    painter = painterResource(id = R.drawable.arrow_downward_24px),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .border(width = 2.dp, color = Color.White, shape = CircleShape)
                        .clip(CircleShape)
                        .background(color = Color.WhiteBg)
                        .padding(8.dp)
                        .align(alignment = Alignment.Center)
                )
            }


            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(key1 = Unit) {
                focusRequester.requestFocus()
            }

            AppTextField(
                hint = stringResource(R.string._0_sum),
//                value = buildAnnotatedString {
//                    append(sum.value)
//                    if(sum.value.isNotBlank())
//                        withStyle(style = SpanStyle(color = Color.HintUzum)) { append(" sum") }
//                }.toString(),
                value = sum.value,
                onValueChange = { onEventDispatcher(TransferFromToContract.Intent.SumChange(it)) },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                    .height(56.dp)
                    .focusRequester(focusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    if (uiState.value.sumLoading) {
                        CircularProgressIndicator(
                            color = Color.PinkUzum,
                            strokeCap = StrokeCap.Square,
                            strokeWidth = 4.dp,
                            modifier = Modifier.scale(0.5f)
                        )
                    }
                },
                visualTransformation = AmountVisual()
            )

            Text(
                text = uiState.value.errorSum.first,
                fontFamily = fontFamilyUzum,
                fontWeight = FontWeight.Medium,
                color = if (uiState.value.errorSum.second) Color.Red else Color.HintUzum,
                fontSize = 12.sp,
                lineHeight = 14.sp,
                modifier = Modifier.padding(start = 28.dp, end = 32.dp, top = 4.dp)
            )
        }


    }
}
