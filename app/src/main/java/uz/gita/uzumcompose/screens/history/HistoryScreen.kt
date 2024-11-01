package uz.gita.uzumcompose.screens.history

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.common.data.HomeData
import uz.gita.presentation.cards.transferFromTo.TransferFromToContract
import uz.gita.presentation.cards.verifyTransfer.VerifyTransferContract
import uz.gita.presentation.helper.extensions.toTime
import uz.gita.presentation.history.HistoryContract
import uz.gita.presentation.history.HistoryVM
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.components.NetworkErrorDialog
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.DarkGreenUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.PinkUzumPlain
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.WhiteBg
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.ui.theme.fontUzumPro
import uz.gita.uzumcompose.utils.extensions.formatToMoney
import uz.gita.uzumcompose.utils.extensions.toPrivatePan

class HistoryScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: HistoryContract.ViewModel = getViewModel<HistoryVM>()
        HistoryContent(viewModel.collectAsState(), viewModel::onEventDispatcher)
    }
}

@Preview
@Composable
fun HistoryContentPrev() {
    UzumComposeTheme {
        HistoryContent(
            remember { mutableStateOf(HistoryContract.UIState()) },
            {}
        )
    }
}

@Composable
fun HistoryContent(
    uiState: State<HistoryContract.UIState>,
    onEventDispatcher: (HistoryContract.Intent) -> Unit
) {

    val pagingItems = uiState.value.history.collectAsLazyPagingItems()

    if (uiState.value.networkDialog) {
        NetworkErrorDialog { onEventDispatcher(HistoryContract.Intent.NetworkDismiss) }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(color = Color.WhiteBg)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back arrow",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(
                            onClick = {
//                                onEventDispatcher(TransferFromToContract.Intent.Back)
                            }
                        )
                        .padding(8.dp)
                )

                Text(
                    text = stringResource(R.string.monitoring),
                    color = Color.BlackUzum,
                    fontSize = 18.sp,
                    fontFamily = fontFamilyUzum,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.weight(1f))


                Icon(
                    painter = painterResource(id = R.drawable.ic_filter_clicked),
                    contentDescription = "filter",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(
                            onClick = {
//                                onEventDispatcher(TransferFromToContract.Intent.Back)
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
                .background(color = Color.WhiteBg)
        ) {

            val list = listOf(
                HomeData.TransferInfo("income", "Mirzaliyev Po'lat", "9860111122223333", "1000", "1730455480237"),
                HomeData.TransferInfo("outcome", "Mirzaliyev Po'lat", "9860111122223333", "1000", "1730455480237"),
                HomeData.TransferInfo("income", "Mirzaliyev Po'lat", "9860111122223333", "1000", "1730455480237"),
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                Log.d("AAA", "HistoryContent: ${pagingItems.itemCount}")
                items(pagingItems.itemCount) { index ->
                    val transferInfo = pagingItems[index]

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .fillMaxWidth()
                            .aspectRatio(4 / 1.1f)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(
                                id = R.drawable.ic_convert_default
                            ),
                            contentDescription = null,

                            )

                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = transferInfo?.to?.toPrivatePan() ?: "",
                                    color = Color.BlackUzum,
                                    fontSize = 16.sp,
                                    fontFamily = fontUzumPro,
                                    fontWeight = FontWeight.SemiBold,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 16.dp)
                                )

                                Text(
                                    text = transferInfo?.type?.capitalize() ?: "",
                                    color = Color.PinkUzum,
                                    fontSize = 12.sp,
                                    fontFamily = fontUzumPro,
                                    fontWeight = FontWeight.Medium,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 12.sp,
                                    modifier = Modifier
                                        .background(
                                            color = Color.PinkUzumPlain.copy(alpha = 0.4f),
                                            shape = RoundedCornerShape(6.dp)
                                        )
                                        .padding(6.dp)
                                )

                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = transferInfo?.time?.toTime() ?: "",
                                    color = Color.HintUzum,
                                    fontSize = 14.sp,
                                    fontFamily = fontUzumPro,
                                    fontWeight = FontWeight.Medium,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .padding(start = 16.dp)
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                val isIncome = transferInfo?.type == "income"
                                val prefix = if (isIncome) "+" else "-"
                                val formatted = formatToMoney(transferInfo?.amount.toString())
                                val finalText = "$prefix$formatted UZS"

                                Text(
                                    text = finalText,
                                    color = if (isIncome) Color.DarkGreenUzum else Color.Red,
                                    fontSize = 16.sp,
                                    fontFamily = fontUzumPro,
                                    fontWeight = FontWeight.SemiBold,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                )


                            }
                        }
                    }

                }
            }

        }
    }

}