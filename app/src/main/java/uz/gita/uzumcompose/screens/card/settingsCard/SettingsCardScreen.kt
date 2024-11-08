package uz.gita.uzumcompose.screens.card.settingsCard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.presentation.cards.settingsCard.SettingsCardContract
import uz.gita.presentation.cards.settingsCard.SettingsCardVM
import uz.gita.presentation.cards.updateCard.UpdateCardContract
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.components.CardUpdateDialog
import uz.gita.uzumcompose.ui.components.ThemeSwitcher
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.WhiteBg
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.utils.extensions.formatToMoney
import uz.gita.uzumcompose.utils.extensions.toCardImage

class SettingsCardScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: SettingsCardContract.ViewModel = getViewModel<SettingsCardVM>()

        SettingsCardContent(viewModel.collectAsState(), viewModel::onEventDispatcher)
    }
}

@Preview
@Composable
fun SettingsCardPrev() {
    UzumComposeTheme {
        SettingsCardContent(
            remember { mutableStateOf(SettingsCardContract.UIState()) },
            {},
        )
    }
}


@Composable
fun SettingsCardContent(
    uiState: State<SettingsCardContract.UIState>,
    onEventDispatcher: (SettingsCardContract.Intent) -> Unit,
) {

    BackHandler {
        onEventDispatcher(SettingsCardContract.Intent.Back)
    }

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
    }


    if (uiState.value.saveDialog) {
        CardUpdateDialog(
            yesClick = { onEventDispatcher(SettingsCardContract.Intent.YesClick) },
            noClick = { onEventDispatcher(SettingsCardContract.Intent.NetworkDialogDismiss(true)) },
            onDismissRequest = { onEventDispatcher(SettingsCardContract.Intent.NetworkDialogDismiss(false)) },
            showProgress = uiState.value.saveLoading
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
                        .clip(CircleShape)
                        .clickable(
                            onClick = {
                                onEventDispatcher(SettingsCardContract.Intent.Back)
                            }
                        )
                        .padding(8.dp)
                )

                Text(
                    text = stringResource(R.string.cards),
                    color = Color.BlackUzum,
                    fontSize = 18.sp,
                    fontFamily = fontFamilyUzum,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 8.dp)
                )

            }
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
//                .padding(vertical = 16.dp)
        ) {

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.theme),
                    color = Color.BlackUzum,
                    fontSize = 14.sp,
                    fontFamily = fontFamilyUzum,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(60.dp)
                )

                Text(
                    text = stringResource(R.string.info),
                    color = Color.BlackUzum,
                    fontSize = 14.sp,
                    fontFamily = fontFamilyUzum,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                )

                Text(
                    text = stringResource(R.string.visibility),
                    color = Color.BlackUzum,
                    fontSize = 14.sp,
                    fontFamily = fontFamilyUzum,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                )


            }

            LazyColumn {
                itemsIndexed(uiState.value.myCards) { index,cardInfo ->
//                items(3) { cardInfo ->
                    Row(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .height(68.dp)
                            .fillMaxWidth()
//                            .clickable {
////                                onEventDispatcher(TransferFromToContract.Intent.CardChoose(cardInfo))
//                            }
                            .padding(vertical = 12.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = cardInfo.themeType.toCardImage()),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(40.dp)
                                .width(60.dp)
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
                                text = formatToMoney(cardInfo.amount) + " sum",
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

                        val isVisible = uiState.value.myCards[index].isVisible.toBoolean()
                        ThemeSwitcher(
                            darkTheme = isVisible,
                            onClick = {
                                onEventDispatcher(SettingsCardContract.Intent.ToggleClick(!isVisible, index))
                            },
                            size = 25.dp,
                            modifier = Modifier.padding(vertical = 4.dp),
                            thumbPadding = 2.dp
                        )


                    }
                }
            }


            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .height(68.dp)
                    .fillMaxWidth()
                    .clickable {
                        onEventDispatcher(SettingsCardContract.Intent.AddCard)
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