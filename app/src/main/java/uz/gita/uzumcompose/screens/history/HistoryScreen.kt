package uz.gita.uzumcompose.screens.history

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.common.data.HomeData
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
        HistoryContent(remember { mutableStateOf(HistoryContract.UIState()) }, {})
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryContent(
    uiState: State<HistoryContract.UIState>, onEventDispatcher: (HistoryContract.Intent) -> Unit
) {

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.WhiteBg, darkIcons = true
        )
    }



    if (uiState.value.networkDialog) {
        NetworkErrorDialog { onEventDispatcher(HistoryContract.Intent.NetworkDismiss) }
    }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.value.isLoading,
        onRefresh = { onEventDispatcher(HistoryContract.Intent.UpdateClick) })
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

                Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back arrow",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(onClick = {
                            onEventDispatcher(HistoryContract.Intent.Back)
                        })
                        .padding(8.dp))

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


                Icon(painter = painterResource(id = R.drawable.ic_filter_clicked),
                    contentDescription = "filter",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(onClick = {
//                                onEventDispatcher(TransferFromToContract.Intent.Back)
                        })
                        .padding(8.dp))
            }
        }, modifier = Modifier.fillMaxSize()
    ) {


        val pagingItems = uiState.value.history.collectAsLazyPagingItems()

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .background(color = Color.WhiteBg)
        ) {


            LazyColumn(
                modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp)
            ) {

                items(pagingItems.itemCount) { index ->
                    val transferInfo = pagingItems[index].also { Log.d("TAG", "HistoryContent:$it ") }

                    if (transferInfo != null) {
                        RealItem(transferInfo)
                    }
                }
                pagingItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading  -> {
                            item { PlaceholderItem(Modifier.fillMaxSize()) }
                        }
                        loadState.refresh is LoadState.NotLoading && itemCount == 0 -> {
                            item { NoTransactions(Modifier.fillMaxWidth()) }
                        }
                        loadState.append is LoadState.Loading -> {
                            item { PlaceholderItem(Modifier.fillMaxWidth()) }
                        }
                    }
                }
            }


        }


        Box(
            modifier = Modifier
                .padding(top = 56.dp)
                .fillMaxWidth()
        ) {
            PullRefreshIndicator(
                refreshing = uiState.value.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                backgroundColor = Color.White,
                contentColor = Color.PinkUzum
            )
        }
    }

}

@Composable
fun RealItem(transferInfo: HomeData.TransferInfo?) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth()
            .aspectRatio(4 / 1.1f)
            .background(
                color = Color.White, shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp), verticalAlignment = Alignment.CenterVertically
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
                    .weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (transferInfo?.type == "outcome") transferInfo.from.toPrivatePan()
                    else transferInfo?.to?.toPrivatePan() ?: "",
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
                            color = Color.PinkUzumPlain.copy(alpha = 0.4f), shape = RoundedCornerShape(6.dp)
                        )
                        .padding(6.dp)
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = transferInfo?.time?.toTime() ?: "",
                    color = Color.HintUzum,
                    fontSize = 14.sp,
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 16.dp)
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
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun PlaceholderItem(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(16.dp), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


@Composable
fun NoTransactions(modifier: Modifier) {
    Box(
        modifier = modifier.padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.no_transactions),
            color = Color.BlackUzum,
            fontSize = 16.sp,
            fontFamily = fontFamilyUzum,
            fontWeight = FontWeight.Normal,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
