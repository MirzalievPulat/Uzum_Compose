package uz.gita.uzumcompose.presentation.pages.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import uz.gita.common.data.CardData
import uz.gita.uzumcompose.R
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
import uz.gita.uzumcompose.utils.extensions.formatToMoney

class HomePage : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.bottom_nav_home)
            val icon = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_home_active_24))

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        UzumComposeTheme {
            HomeContent()
        }
    }
}

//@Preview
@Composable
private fun HomePreview(modifier: Modifier = Modifier) {
    UzumComposeTheme {
        HomeContent()
    }
}

@Composable
private fun HomeContent(modifier: Modifier = Modifier) {
    val c = LocalConfiguration.current
    val screenWidth = c.screenWidthDp.dp.value
    val screenHeight = c.screenHeightDp.dp.value
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.PinkUzum,
            darkIcons = false
        )
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
            SearchSection()
            Spacer(modifier = Modifier.height(32.dp))
            CardSection()

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
                        Spacer(modifier = Modifier.height(34.dp))
                        CashbackMonitoringSection()
                        Spacer(modifier = Modifier.height(32.dp))
                        FastAccessSection()
                        Spacer(modifier = Modifier.height(16.dp))
                        BillBoardSection()
                        Spacer(modifier = Modifier.height(16.dp))
                        LocalPaymentSection()
                    }
                }
                //refresh button
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(color = Color.TextField)
                        .align(Alignment.TopCenter)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.refresh_button_gradient),
                        contentDescription = "Refresh",
                        modifier = Modifier.padding(8.dp)
                    )

                }

            }

            //
            //
            //
            //SavedPaymentsSection()
            //HistorySection()
        }
    }
}

//@Preview
@Composable
fun SearchSection() {
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
                .background(color = Color.PinkUzumPlain),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_person
                ),
                contentDescription = "Person",
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
    }
//    }

}

val cardList = listOf(
    CardData.CardInfo(amount = "10000", pan = "0987", themeType = "0"),
    CardData.CardInfo(amount = "3000", pan = "0912", themeType = "1"),
    CardData.CardInfo(amount = "100000", pan = "2356", themeType = "2"),
    CardData.CardInfo(amount = "5000000", pan = "0985", themeType = "3"),
)

@Composable
fun CardSection() {
    var totalSum by remember { mutableLongStateOf(55300) }
    val moneyCurrency by remember { mutableStateOf("UZS") }
    val moneyVisible by remember { mutableStateOf(true) }

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
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = formatToMoney(totalSum),
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
                    .padding(bottom = 2.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = if (moneyVisible) painterResource(id = R.drawable.ic_eye_close_24) else painterResource(id = R.drawable.ic_eye_24),
                contentDescription = "Visibility",
            )

            Spacer(modifier = Modifier.width(32.dp))
        }
        Spacer(modifier = Modifier.height(24.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cardList) { card ->
                Card(
                    modifier = Modifier
                        .width(130.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(R.drawable.ic_limit_card),
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
                                text = formatToMoney(card.amount.toLong()) + " UZS",
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
fun CashbackMonitoringSection() {
//    Surface(modifier = Modifier.fillMaxSize()) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Card(
            modifier = Modifier
                .height(125.dp)
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
                        text = "1 020",
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
                .height(125.dp)
                .weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
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
        Spacer(modifier = Modifier.height(8.dp))
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
    val text:String,
    val title:String,
    val image:Int,
    val color:Color
)
@OptIn(ExperimentalFoundationApi::class)
//@Preview
@Composable
fun BillBoardSection() {
    val pagerState = rememberPagerState()

    val list = listOf(
        BillBoard("Low prices \nevery day","Go shopping",R.drawable.uzum_shagzoda,Color.PinkUzumPlain),
        BillBoard("Buy or sell your car \nfast and easy","With Uzum Auto",R.drawable.uzum_avto,Color.White),
        BillBoard("-20,000 UZS for \nthe first 3 orders of \nfood in Uzum Tezkor","Promocode TEZ3",R.drawable.uzum_tezkor,Color.BlueUzum),
        BillBoard("Spend your \ninstalment limit \nin Uzum Tezkor","Promocode TEZ3",R.drawable.uzum_alisher,Color.FireUzum),
        BillBoard("The best delivery \nis in Uzum Market","Order",R.drawable.uzum_yetkazish,Color.HintUzum),
        )

    CompositionLocalProvider(value = LocalOverscrollConfiguration provides null) {
        HorizontalPager(
//            modifier = Modifier.height(250.dp),
            count = Int.MAX_VALUE,
            contentPadding = PaddingValues(horizontal = 30.dp),
            itemSpacing = 12.dp,
            state = pagerState
        ) { index ->
            list.getOrNull(
                index % (list.size)
            )?.let { item ->
                BillBoardCard(item)
            }
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        var initPage = Int.MAX_VALUE / 2
        while (initPage % list.size != 0) {
            initPage++
        }
        pagerState.scrollToPage(initPage)
    })


}

@Composable
fun BillBoardCard(billBoard: BillBoard) {
//    Surface(modifier = Modifier.fillMaxSize()) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(7 / 2f)
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
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp))
                }


//                    Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = billBoard.image),
                contentDescription = "Uzum image",
//                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }

//    }

}

@Preview
@Composable
fun LocalPaymentSection(modifier: Modifier = Modifier) {
    Surface(modifier = Modifier.fillMaxSize()) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(8.dp))
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
        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
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
    }
}