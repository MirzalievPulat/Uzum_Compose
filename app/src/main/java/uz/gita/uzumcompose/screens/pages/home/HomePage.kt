package uz.gita.uzumcompose.screens.pages.home

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.presentation.helper.extensions.toTime
import uz.gita.presentation.pages.home.HomePageContract
import uz.gita.presentation.pages.home.HomePageVM
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.screens.main.PolatTab
import uz.gita.uzumcompose.screens.main.PolatTabOptions
import uz.gita.uzumcompose.ui.components.NetworkErrorDialog
import uz.gita.uzumcompose.ui.components.TransparentTextField
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.BlackUzum2
import uz.gita.uzumcompose.ui.theme.BlueUzum
import uz.gita.uzumcompose.ui.theme.DarkGreenUzum
import uz.gita.uzumcompose.ui.theme.FireUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.PinkUzumPlain
import uz.gita.uzumcompose.ui.theme.TextField
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.ui.theme.fontUzumPro
import uz.gita.uzumcompose.utils.extensions.drawDashedBorder
import uz.gita.uzumcompose.utils.extensions.formatToMoney
import uz.gita.uzumcompose.utils.extensions.toCardImage
import uz.gita.uzumcompose.utils.extensions.toPrivatePan


//adamari

object HomePage : PolatTab {
    private fun readResolve(): Any = HomePage
    override val polatTabOptions: PolatTabOptions
        @Composable
        get() {
            val title = stringResource(R.string.bottom_nav_home)
            val selectedIcon = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_home_active_24))
            val unSelectedIcon = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_home_24))

            return remember {
                PolatTabOptions(
                    index = 0u,
                    title = title,
                    selectedIcon = selectedIcon,
                    unSelectedIcon = unSelectedIcon
                )
            }
        }

    @Composable
    override fun Content() {
        UzumComposeTheme {
            val viewModel: HomePageContract.ViewModel = getViewModel<HomePageVM>()
            HomeContent(viewModel.collectAsState(), viewModel::onEventDispatcher)

        }
    }
}

@Preview
@Composable
private fun HomePreview(modifier: Modifier = Modifier) {
    UzumComposeTheme {
        HomeContent(uiState = remember {
            mutableStateOf(HomePageContract.UIState())
        }
        ) {
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeContent(
    uiState: State<HomePageContract.UIState>,
    onEventDispatcher: (HomePageContract.Intent) -> Unit
) {
    val c = LocalConfiguration.current
    val screenWidth = c.screenWidthDp.dp.value
    val screenHeight = c.screenHeightDp.dp.value
    val pullRefreshState =
        rememberPullRefreshState(refreshing = uiState.value.isLoading,
            onRefresh = { onEventDispatcher(HomePageContract.Intent.UpdateClick) })


    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.PinkUzum,
            darkIcons = false
        )
    }

    if (uiState.value.networkDialog) {
        NetworkErrorDialog { onEventDispatcher(HomePageContract.Intent.NetworkCloseClick) }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                },
                containerColor = Color.PinkUzum,
                modifier = Modifier
                    .imePadding()
                    .height(48.dp)
            ) {
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_qr_uzumpay),
                        contentDescription = "null",
                        tint = Color.White
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.PinkUzumPlain, Color.PinkUzum),
                        radius = screenWidth * 1.5f,
                        center = Offset(x = screenWidth * (1.1f), y = screenHeight / (1.7f))
                    )
                )
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            SearchSection(onEventDispatcher)
            Spacer(modifier = Modifier.height(32.dp))
            CardSection(uiState, onEventDispatcher)

            Spacer(modifier = Modifier.height(44.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                //whole white space
                Box(
                    modifier = Modifier
                        .padding(top = 18.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color.TextField,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(36.dp))
                        CashbackMonitoringSection(uiState, onEventDispatcher)
                        Spacer(modifier = Modifier.height(32.dp))
                        FastAccessSection()
                        Spacer(modifier = Modifier.height(16.dp))
                        BillBoardSection()
                        Spacer(modifier = Modifier.height(18.dp))
                        LocalPaymentSection()
                        Spacer(modifier = Modifier.height(16.dp))
                        MobileNumberSection()
                        Spacer(modifier = Modifier.height(32.dp))
                        SavedPaymentsSection()
                        Spacer(modifier = Modifier.height(16.dp))
                        HistorySection(uiState, onEventDispatcher)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                //refresh button
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(color = Color.TextField)
                        .align(Alignment.TopCenter)
                        .clickable {
                            onEventDispatcher.invoke(
                                HomePageContract.Intent.UpdateClick
                            )
                            Log.d("TAG", "HomeContent: bosildi")
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.refresh_button_gradient),
                        contentDescription = "Refresh",
                        modifier = Modifier
                            .background(color = Color.Transparent, CircleShape)
                            .clip(CircleShape)
                            .clickable { onEventDispatcher(HomePageContract.Intent.UpdateClick) }
                            .padding(8.dp)
                    )

                }


            }

        }
        Box(modifier = Modifier.fillMaxWidth()) {
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

//@Preview
@Composable
fun SearchSection(onEventDispatcher: (HomePageContract.Intent) -> Unit) {
//    Surface(modifier = Modifier.fillMaxSize()) {
    println("SearchSection")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            modifier = Modifier
                .height(36.dp)
                .weight(1f)
                .clip(CircleShape)
                .background(color = Color.PinkUzumPlain),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_search_24),
                contentDescription = "Search",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.searchBar_text),
                style = TextStyle(
                    color = Color.White,
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            )
        }
        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(color = Color.PinkUzumPlain)
                .clickable { onEventDispatcher(HomePageContract.Intent.ProfileClick) },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = "Person",
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
    }
//    }

}

//val cardList = listOf(
//    CardData.CardInfo(amount = "100005000", pan = "0987", themeType = "0"),
//    CardData.CardInfo(amount = "3000", pan = "0912", themeType = "1"),
//    CardData.CardInfo(amount = "100000", pan = "2356", themeType = "2"),
//    CardData.CardInfo(amount = "5000000", pan = "0985", themeType = "3"),
//)

@Composable
fun CardSection(
    uiState: State<HomePageContract.UIState>,
    onEventDispatcher: (HomePageContract.Intent) -> Unit
) {
//    var totalSum by remember { mutableLongStateOf(100055300) }
    val moneyCurrency by remember { mutableStateOf("UZS") }
    val animatedBalance by animateIntAsState(
        targetValue = uiState.value.totalBalance.toInt(),
        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
    )


    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(32.dp))
            Image(
                painter = painterResource(
                    id = R.drawable.ic_settings_main
                ),
                contentDescription = "Settings",
                modifier = Modifier
                    .background(color = Color.Transparent, CircleShape)
                    .clip(CircleShape)
                    .clickable { onEventDispatcher(HomePageContract.Intent.SettingsClick) }
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = if (uiState.value.isMoneyVisible) formatToMoney(animatedBalance)
                else "********",
                style = TextStyle(
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = moneyCurrency,
                style = TextStyle(
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                ), modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = if (uiState.value.isMoneyVisible) painterResource(id = R.drawable.ic_eye_close_24)
                else painterResource(id = R.drawable.ic_eye_24),
                contentDescription = "Visibility",
                modifier = Modifier
                    .background(color = Color.Transparent, CircleShape)
                    .clip(CircleShape)
                    .clickable {
                        onEventDispatcher(HomePageContract.Intent.EyeClick)
                    }
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.width(32.dp))
        }
        Spacer(modifier = Modifier.height(24.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.value.cardList) { card ->
                Card(
                    modifier = Modifier
                        .width(130.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            onEventDispatcher(HomePageContract.Intent.CardClick(card))
                        }
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(card.themeType.toCardImage()),
                            contentDescription = "Card background",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        Box(
                            modifier = Modifier.align(Alignment.TopCenter)
//                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 8.dp),
                                text = if (uiState.value.isMoneyVisible) formatToMoney(card.amount) + " UZS"
                                else "******** UZS",
                                style = TextStyle(
                                    color = Color.White,
                                    fontFamily = fontUzumPro,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp
                                ),
                            )
                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(8.dp)
//                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier,
                                text = card.pan,
                                style = TextStyle(
                                    color = Color.White,
                                    fontFamily = fontUzumPro,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 11.sp
                                ),
                            )
                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_new_uzcard_logo),
                                contentDescription = "card type",
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(16.dp)

                            )
                        }
                    }
                }

            }
        }
    }
}

//@Preview
@Composable
fun CashbackMonitoringSection(
    uiState: State<HomePageContract.UIState>,
    onEventDispatcher: (HomePageContract.Intent) -> Unit
) {
//    Surface(modifier = Modifier.fillMaxSize()) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Card(
            modifier = Modifier
                .height(130.dp)
                .weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            )
            {
                Text(
                    text = buildAnnotatedString {
                        append("Cashback up to ")
                        withStyle(
                            style = SpanStyle(
                                color = Color.DarkGreenUzum,
                                fontSize = 13.sp,
                                fontFamily = fontUzumPro,
                                fontWeight = FontWeight.SemiBold
                            )
                        ) { append("20%") }
                    },
                    style = TextStyle(
                        color = Color.Black,
                        fontFamily = fontUzumPro,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = if (uiState.value.isMoneyVisible) "1 020" else "*****",
                        style = TextStyle(
                            color = Color.Black,
                            fontFamily = fontUzumPro,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "UZS",
                        style = TextStyle(
                            color = Color.Black,
                            fontFamily = fontUzumPro,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )

                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Earned",
                    style = TextStyle(
                        color = Color.HintUzum,
                        fontFamily = fontUzumPro,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Card(
            modifier = Modifier
                .height(130.dp)
                .weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            onClick = {
                onEventDispatcher(HomePageContract.Intent.MonitoringClick)
            },
        ) {
            Column(
                modifier = Modifier
                    .background(color = Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Monitoring",
                    style = TextStyle(
                        color = Color.Black,
                        fontFamily = fontUzumPro,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.ic_monitoring_chart),
                    contentDescription = "Diagram",
//                        modifier = Modifier.fillMaxSize()
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
//    }

}

data class FastAccess(
    val icon: Int,
    val title: String,
)

val fastAccessList = listOf(
    FastAccess(R.drawable.ic_phone_pink, "My number"),
    FastAccess(R.drawable.ic_my_home, "My home"),
    FastAccess(R.drawable.ic_humo_pay, "HUMO Pay"),
    FastAccess(R.drawable.ic_exchange, "Exchange"),
    FastAccess(R.drawable.ic_deposit, "Deposit"),
    FastAccess(R.drawable.ic_card_new_icon, "Loans"),
    FastAccess(R.drawable.icon_currency_rate, "Exchange"),
    FastAccess(R.drawable.icon_wallet, "Wallets"),
)

//@Preview
@Composable
fun FastAccessSection(modifier: Modifier = Modifier) {
//    Surface(modifier = Modifier.fillMaxSize()) {
    Column(modifier = Modifier.fillMaxWidth()) {
//        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.txt_fast_access),
                style = TextStyle(
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.HintUzum
                ),
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_right_round_arrow),
                contentDescription = "More",
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            items(fastAccessList) { item ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(color = Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(item.icon),
                            tint = Color.Unspecified,
                            contentDescription = "Icon"
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = item.title,
                        style = TextStyle(
                            fontFamily = fontUzumPro,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = Color.BlackUzum2
                        )
                    )
                }
            }
        }
    }
//    }
}

data class BillBoard(
    val text: String,
    val title: String,
    val image: Int,
    val color: Color
)

@OptIn(ExperimentalFoundationApi::class)
//@Preview
@Composable
fun BillBoardSection() {


    val list = listOf(
        BillBoard("Low prices \nevery day", "Go shopping", R.drawable.uzum_shagzoda, Color.PinkUzumPlain),
        BillBoard("Buy or sell your car \nfast and easy", "With Uzum Auto", R.drawable.uzum_avto, Color.White),
        BillBoard(
            "-20,000 UZS for \nthe first 3 orders of \nfood in Uzum Tezkor",
            "Promocode TEZ3",
            R.drawable.uzum_tezkor,
            Color.BlueUzum
        ),
        BillBoard("Spend your \ninstalment limit \nin Uzum Tezkor", "Promocode TEZ3", R.drawable.uzum_alisher, Color.FireUzum),
        BillBoard("The best delivery \nis in Uzum Market", "Order", R.drawable.uzum_yetkazish, Color.HintUzum),
    )

    val pagerState = androidx.compose.foundation.pager.rememberPagerState(pageCount = { Int.MAX_VALUE })

    LaunchedEffect(key1 = Unit, block = {
        var initPage = Int.MAX_VALUE / 2
        while (initPage % list.size != 0) {
            initPage++
        }
        pagerState.scrollToPage(initPage)
    })


    CompositionLocalProvider(value = LocalOverscrollConfiguration provides null) {
        androidx.compose.foundation.pager.HorizontalPager(
            contentPadding = PaddingValues(horizontal = 30.dp),
            pageSpacing = 12.dp,
            state = pagerState
        ) { index ->
            list.getOrNull(
                index % (list.size)
            )?.let { item ->
                BillBoardCard(item)
            }
        }
    }

}

@Composable
fun BillBoardCard(billBoard: BillBoard) {
//    Surface(modifier = Modifier.fillMaxSize()) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(9 / 3f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .background(color = billBoard.color, shape = RoundedCornerShape(16.dp))
                .fillMaxHeight(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
//                    Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = billBoard.text,
                    modifier = Modifier.width(150.dp),
                    style = TextStyle(
                        fontFamily = fontFamilyUzum,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = billBoard.title,
                        style = TextStyle(
                            fontFamily = fontFamilyUzum,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                }


//                    Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = billBoard.image),
                contentDescription = "Uzum image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }

//    }

}


data class LocalePay(
    val icon: Int,
    val name: String,
    val distance: Float
)

val localPayList = arrayListOf(
    LocalePay(R.drawable.locale_adamari, "Adamari Yunusobod  Jenskiy", 0f),
    LocalePay(R.drawable.locale_registann, "Registon LC Shaxriston", 0.1f),
    LocalePay(R.drawable.locale_istanbul_baklavaa, "Istanbul baklava", 1f),
    LocalePay(R.drawable.localse_mosque, "Nimadur mosque", 2f),
    LocalePay(R.drawable.locale_safiaa, "Sofia Yunusobod", 3f),
    LocalePay(R.drawable.locale_turkish_babyy, "Turkish baby", 3f),
    LocalePay(R.drawable.locale_stirkaa, "Stirka uz ummidingni tezda uz", 4.4f),
)

//@Preview
@Composable
fun LocalPaymentSection() {
//    Surface(modifier = Modifier.fillMaxSize()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.txt_local_payments),
                style = TextStyle(
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.HintUzum
                ),
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_right_round_arrow),
                contentDescription = "More",
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }

        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(localPayList) { item ->
                Row(
                    modifier = Modifier
                        .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                        .width(250.dp)
                        .height(100.dp)
                        .padding(16.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .border(
                                width = 1.dp,
                                color = Color.HintUzum,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .size(36.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = item.icon),
                            contentDescription = "Icon",
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.name,
                                color = Color.BlackUzum,
                                fontSize = 16.sp,
                                fontFamily = fontUzumPro,
                                fontWeight = FontWeight.Medium,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                lineHeight = 18.sp,
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(R.drawable.ic_my_location),
                                contentDescription = "icon",
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(18.dp)
                            )
                            Text(
                                text = "${item.distance} km",
                                color = Color.HintUzum,
                                fontSize = 16.sp,
                                fontFamily = fontUzumPro,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
//    }
}

//@Preview
@Composable
fun MobileNumberSection() {

    var phoneNumber by remember { mutableStateOf("") }

//    Surface(modifier = Modifier.fillMaxSize()) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .aspectRatio(8f / 2f)
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.txt_mobile_number),
            fontFamily = fontUzumPro,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Color.BlackUzum2
        )

        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            TransparentTextField(
                hint = "(00) 000-00-00",
                value = phoneNumber,
                onValueChange = {
                    if (it.length <= 9 && (it.isDigitsOnly() || it.isEmpty())) phoneNumber = it
                },
                leadingIcon = {
                    Text(
                        text = "+998 ",
                        fontFamily = fontUzumPro,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = Color.BlackUzum
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Go
                )

            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = "person",
                tint = Color.HintUzum,
                modifier = Modifier
            )
        }
    }
//    }
}

//@Preview
@Composable
fun SavedPaymentsSection() {
//    Surface(modifier = Modifier.fillMaxSize()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.txt_saved_payments),
                style = TextStyle(
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.HintUzum
                ),
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_right_round_arrow),
                contentDescription = "More",
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }

        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .drawDashedBorder(
                        color = Color.LightGray,
                        strokeWidth = 3f,
                        cornerRadius = 100f,
                        dashLength = 10f,
                        gapLength = 10f
                    )
                    .size(54.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "add",
                    tint = Color.HintUzum
                )
            }

            Text(
                text = stringResource(R.string.txt_add),
                color = Color.BlackUzum2,
                fontFamily = fontUzumPro,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 12.dp)
            )
        }


//        }
    }
}

//@Preview
@Composable
fun HistorySection(
    uiState: State<HomePageContract.UIState>,
    onEventDispatcher: (HomePageContract.Intent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.txt_history),
                style = TextStyle(
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.HintUzum
                ),
            )
            Image(
                painter = painterResource(id = R.drawable.ic_right_round_arrow),
                contentDescription = "More",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onEventDispatcher(HomePageContract.Intent.MonitoringClick) }
                    .padding(8.dp)
            )
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(bottom = 32.dp)
                .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .clickable { onEventDispatcher(HomePageContract.Intent.MonitoringClick) }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (uiState.value.lastTransfersLoading){
                CircularProgressIndicator(
                    color = Color.PinkUzum,
                    strokeCap = StrokeCap.Square,
                    strokeWidth = 4.dp,
                    modifier = Modifier.padding(vertical = 8.dp).scale(1f)
                )
            }else if (uiState.value.lastTransfers.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_history),
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.BlackUzum,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    maxLines = 1
                )
            } else {

                repeat(3) {

                    val transferInfo = uiState.value.lastTransfers[it]

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_convert_default),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .size(40.dp)
                        )

                        Column(modifier = Modifier.padding(start = 16.dp)) {
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = if (transferInfo.type == "outcome") transferInfo.to.toPrivatePan()
                                    else transferInfo.from.toPrivatePan(),
                                    fontFamily = fontUzumPro,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp,
                                    color = Color.BlackUzum,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .weight(7f)
                                        .padding(end = 16.dp),
                                    maxLines = 1
                                )


                                val isIncome = transferInfo.type == "income"
                                val prefix = if (isIncome) "+" else "-"
                                val formatted = formatToMoney(transferInfo.amount.toString())
                                val finalText = "$prefix$formatted"
                                Text(
                                    text = finalText,
                                    fontFamily = fontUzumPro,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp,
                                    color = Color.BlackUzum,
                                    modifier = Modifier.weight(3f),
                                    maxLines = 1,
                                    textAlign = TextAlign.End
                                )

                            }
                            Row(verticalAlignment = Alignment.Top) {
                                Text(
                                    text = "Tansaction",
                                    fontFamily = fontUzumPro,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 13.sp,
                                    lineHeight = 15.sp,
                                    color = Color.HintUzum,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(end = 16.dp),
                                )
                                Text(
                                    text = transferInfo.time.toTime(),
                                    fontFamily = fontUzumPro,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 13.sp,
                                    lineHeight = 15.sp,
                                    color = Color.HintUzum,
                                    modifier = Modifier.weight(1f),
                                    maxLines = 1,
                                    textAlign = TextAlign.End
                                )

                            }
                        }
                    }
                }

            }
        }
    }
//    }
}