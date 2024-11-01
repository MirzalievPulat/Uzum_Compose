package uz.gita.uzumcompose.screens.card.updateCard

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
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
import uz.gita.common.data.CardData
import uz.gita.presentation.cards.updateCard.UpdateCardContract
import uz.gita.presentation.cards.updateCard.UpdateCardVM
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.components.CardUpdateDialog
import uz.gita.uzumcompose.ui.components.DeleteBottomSheet
import uz.gita.uzumcompose.ui.components.NetworkErrorDialog
import uz.gita.uzumcompose.ui.components.ThemeSwitcher
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.TransparentGray
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.VeryPlainGray
import uz.gita.uzumcompose.ui.theme.WhiteBg
import uz.gita.uzumcompose.ui.theme.fontUzumPro
import uz.gita.uzumcompose.utils.extensions.formatToMoney
import uz.gita.uzumcompose.utils.extensions.toCardImage

class UpdateCardScreen(val cardInfo: CardData.CardInfo) : Screen {
    @Composable
    override fun Content() {
        Log.d("TAG", "Content: cardInfo: $cardInfo")
        val context = LocalContext.current
        val viewModel: UpdateCardContract.ViewModel = getViewModel<UpdateCardVM>()
        viewModel.setCardParam(cardInfo)
        viewModel.collectSideEffect {
            if (it is UpdateCardContract.SideEffect.ToastMessage) {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        UpdateCardContent(cardInfo, viewModel.collectAsState(), viewModel::onEventDispatcher)
    }
}

@Preview
@Composable
fun UpdateCardPrev() {
    UzumComposeTheme {
        UpdateCardContent(
            CardData.CardInfo("", "", "", "", expiredYear = "2024"),
            remember {
                mutableStateOf(UpdateCardContract.UIState())
            },
            {}
        )
    }
}

@Composable
fun UpdateCardContent(
    cardInfo: CardData.CardInfo,
    uiState: State<UpdateCardContract.UIState>,
    onEventDispatcher: (UpdateCardContract.Intent) -> Unit
) {

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.WhiteBg,
            darkIcons = true
        )
    }

    if (uiState.value.updateDialog) {
        CardUpdateDialog(
            yesClick = { onEventDispatcher(UpdateCardContract.Intent.UpdateYesClick) },
            noClick = { onEventDispatcher(UpdateCardContract.Intent.UpdateNoClick) },
            onDismissRequest = { onEventDispatcher(UpdateCardContract.Intent.UpdateDismissClick) },
            showProgress = uiState.value.updateLoading
        )
    }

    if (uiState.value.networkDialog) {
        NetworkErrorDialog { onEventDispatcher(UpdateCardContract.Intent.NetworkDialogDismiss) }
    }

    if (uiState.value.isBottomSheetOpen) {
        DeleteBottomSheet(
            showProgress = uiState.value.deleteLoading,
            yesClick = { onEventDispatcher(UpdateCardContract.Intent.DeleteYesClick(cardInfo.id)) },
            onDismissRequest = { onEventDispatcher(UpdateCardContract.Intent.BottomSheetDismiss) }
        )
    }

   BackHandler {
        onEventDispatcher(UpdateCardContract.Intent.Back)
   }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.WhiteBg)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back arrow",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(
                        onClick = {
                            onEventDispatcher(UpdateCardContract.Intent.Back)
                        }
                    )
                    .padding(8.dp)
            )

            Text(
                text = uiState.value.name,
                color = Color.BlackUzum,
                fontSize = 18.sp,
                fontFamily = fontUzumPro,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 8.dp)
            )
        }


//        var selectedImage by remember { mutableIntStateOf(uiState.value.theme.toCardImage()) }
        Card(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 12.dp)
                .fillMaxWidth()
                .aspectRatio(4 / 2.4f)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(uiState.value.theme.toCardImage()),
                    contentDescription = "Card background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.8f)
                )

                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .aspectRatio(5 / 1.9f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(color = Color.TransparentGray)
                        .align(Alignment.BottomCenter)

                )

                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .aspectRatio(5 / 1.9f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(color = Color.TransparentGray)
                        .align(Alignment.BottomCenter)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                        ) {

                            Text(
                                text = uiState.value.name,
                                color = Color.White,
                                fontFamily = fontUzumPro,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Text(
                                text = formatToMoney(cardInfo.amount) + " UZS",
                                color = Color.White,
                                fontFamily = fontUzumPro,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 22.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Text(
                                text = "**** **** **** " + cardInfo.pan,
                                color = Color.White,
                                fontFamily = fontUzumPro,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Text(
                                text = cardInfo.owner.uppercase(),
                                color = Color.White,
                                fontFamily = fontUzumPro,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentWidth(),
                            horizontalAlignment = Alignment.End,
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_new_humo_logo),
                                contentDescription = "Card background",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(45.dp, 25.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )

                            Box(
                                modifier = Modifier.fillMaxHeight(),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                val expired = if (cardInfo.expiredMonth.length != 1) cardInfo.expiredMonth
                                else "0" + cardInfo.expiredMonth
                                Text(
                                    text = "$expired/${cardInfo.expiredYear.substring(2)}",
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontFamily = fontUzumPro,
                                    fontWeight = FontWeight.Normal,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                )
                            }

                        }

                    }
                }


            }
        }





        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                painter = painterResource(R.drawable.ic_custom_card_bg_1),
                contentDescription = null,
                modifier = Modifier
                    .border(
                        width = 4.dp,
                        color = if (uiState.value.theme.toCardImage() == R.drawable.ic_custom_card_bg_1) Color.PinkUzum
                        else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(4.dp)
                    .width(70.dp)
                    .aspectRatio(4 / 2.4f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        onEventDispatcher(UpdateCardContract.Intent.ThemeClick("1"))
                    },
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(R.drawable.ic_custom_card_bg_2),
                contentDescription = null,
                modifier = Modifier
                    .border(
                        width = 4.dp,
                        color = if (uiState.value.theme.toCardImage() == R.drawable.ic_custom_card_bg_2) Color.PinkUzum
                        else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(4.dp)
                    .width(70.dp)
                    .aspectRatio(4 / 2.4f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        onEventDispatcher(UpdateCardContract.Intent.ThemeClick("2"))
                    },
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(R.drawable.ic_custom_card_bg_3),
                contentDescription = null,
                modifier = Modifier
                    .border(
                        width = 4.dp,
                        color = if (uiState.value.theme.toCardImage() == R.drawable.ic_custom_card_bg_3) Color.PinkUzum
                        else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(4.dp)
                    .width(70.dp)
                    .aspectRatio(4 / 2.4f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onEventDispatcher(UpdateCardContract.Intent.ThemeClick("3")) },
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(R.drawable.ic_custom_card_bg_4),
                contentDescription = null,
                modifier = Modifier
                    .border(
                        width = 4.dp,
                        color = if (uiState.value.theme.toCardImage() == R.drawable.ic_custom_card_bg_4) Color.PinkUzum
                        else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(4.dp)
                    .width(70.dp)
                    .aspectRatio(4 / 2.4f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onEventDispatcher(UpdateCardContract.Intent.ThemeClick("4")) },
                contentScale = ContentScale.Crop
            )

        }


        val focusRequest = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current
        LaunchedEffect(key1 = uiState.value.nameFocused) {
            if (!uiState.value.nameFocused) {
                focusManager.clearFocus()
            } else {
                Log.d("TAG", "UpdateCardContent: request focus")
                focusRequest.requestFocus()
            }
        }
        Text(
            text = stringResource(R.string.card_name),
            color = Color.BlackUzum,
            fontSize = 16.sp,
            fontFamily = fontUzumPro,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )
        OutlinedTextField(
            value = TextFieldValue(uiState.value.name, selection = TextRange(uiState.value.name.length)),
            onValueChange = {
                if (it.text.length < 30) onEventDispatcher(UpdateCardContract.Intent.NameChange(it.text))
            },
            enabled = uiState.value.nameFocused,
            textStyle = TextStyle(
                color = Color.BlackUzum,
                fontSize = 18.sp,
                fontFamily = fontUzumPro,
                fontWeight = FontWeight.Medium,
            ),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.PinkUzum,
                unfocusedIndicatorColor = Color.VeryPlainGray,
                disabledIndicatorColor = Color.VeryPlainGray,

                ),
            trailingIcon = {
                Image(
                    painter = painterResource(
                        id = if (uiState.value.nameFocused) R.drawable.ic_check_circle_active
                        else R.drawable.ic_block_gray_24
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            onEventDispatcher(UpdateCardContract.Intent.NameClick)

                        }
                )
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .focusable(enabled = uiState.value.nameFocused)
                .focusRequester(focusRequest)

        )


//        var isVisible by remember { mutableStateOf(cardInfo.isVisible.toBoolean()) }
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.hide),
                color = Color.BlackUzum,
                fontSize = 16.sp,
                fontFamily = fontUzumPro,
                fontWeight = FontWeight.Medium,
            )
            Spacer(modifier = Modifier.weight(1f))

            ThemeSwitcher(
                darkTheme = uiState.value.isVisible,
                onClick = {
                    onEventDispatcher(UpdateCardContract.Intent.IsVisibleClick(!uiState.value.isVisible))
                },
                size = 25.dp,
                modifier = Modifier.padding(vertical = 4.dp),
                thumbPadding = 2.dp
            )
        }



        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
                .background(color = Color.White)
                .padding(top = 16.dp)

        ) {
            CardMenu(icon = R.drawable.ic_chart_history, name = stringResource(id = R.string.txt_history))
            CardMenu(icon = R.drawable.ic_lock_24, name = stringResource(R.string.safe_mode))
            CardMenu(icon = R.drawable.icon_new_card_type_list, name = stringResource(R.string.limits))
            CardMenu(icon = R.drawable.ic_doc, name = stringResource(R.string.requisites))
            CardMenu(icon = R.drawable.ic_block_24, name = stringResource(R.string.block), false)
            CardMenu(icon = R.drawable.ic_block_24, name = stringResource(R.string.unlock_atto), false)
            CardMenu(icon = R.drawable.ic_delete_24, name = stringResource(R.string.delete), false) {
                onEventDispatcher(UpdateCardContract.Intent.DeleteClick)
            }
        }
    }
}


@Composable
fun CardMenu(
    icon: Int,
    name: String,
    arrow: Boolean = true,
    click: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { click() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .background(color = Color.WhiteBg, shape = RoundedCornerShape(12.dp))
                .padding(6.dp)
        )
        Text(
            text = name,
            color = if (name == "Delete") Color.Red else Color.BlackUzum,
            fontSize = 18.sp,
            fontFamily = fontUzumPro,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        if (arrow) {
            Image(
                painter = painterResource(id = R.drawable.ic_right_round_arrow),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }

    }
}

