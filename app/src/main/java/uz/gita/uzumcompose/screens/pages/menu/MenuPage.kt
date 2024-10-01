package uz.gita.uzumcompose.screens.pages.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.screens.pages.home.localPayList
import uz.gita.uzumcompose.screens.pages.transfer.CardTransferSection
import uz.gita.uzumcompose.screens.pages.transfer.OptionsSection
import uz.gita.uzumcompose.screens.pages.transfer.ShapesSection
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.TextField
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.ui.theme.fontUzumPro

class MenuPage:Tab {
    override val options: TabOptions
        @Composable
        get(){
            val title = stringResource(R.string.bottom_nav_menu)
            val icon = rememberVectorPainter(image = ImageVector.vectorResource(R.drawable.ic_menu))

            return TabOptions(
                index = 4u,
                title = title,
                icon = icon
            )
        }

    @Composable
    override fun Content() {
        MenuPageContent()
    }
}

@Preview
@Composable
fun MenuPreview(modifier: Modifier = Modifier) {
    UzumComposeTheme {
        MenuPageContent()
    }
}

@Composable
fun MenuPageContent(modifier: Modifier = Modifier) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
    }


    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(7 / 1f)
                    .background(color = Color.White)
                    .padding(all = 16.dp)
                ,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = stringResource(R.string.txt_all_services),
                    color = Color.BlackUzum,
                    fontSize = 18.sp,
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.TextField)
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            PopularSection()
        }
    }
}

val popularList = arrayListOf(
    Pair(R.drawable.ic_card_menu,"Cards"),
    Pair(R.drawable.ic_history_menu,"History"),
    Pair(R.drawable.cashback_icon,"Cashback"),
    Pair(R.drawable.icon_currency_rate,"Exchange Rate"),
    Pair(R.drawable.ic_add_round,"Order a card"),
)
@Composable
fun PopularSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.txt_popular),
                style = TextStyle(
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.HintUzum
                ),
            )
        }

        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(popularList) { item ->
                Column(
                    modifier = Modifier
                        .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                        .width(140.dp)
                        .height(120.dp)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box (modifier = Modifier
                        .background(
                            color = Color.PinkUzum,
                            shape = CircleShape
                        )
                        .padding(8.dp)){
                        Icon(imageVector = ImageVector.vectorResource(item.first),
                            contentDescription = "icon",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                            )
                    }

                    Text(text = item.second,
                        color = Color.BlackUzum,
                        fontSize = 16.sp,
                        fontFamily = fontUzumPro,
                        fontWeight = FontWeight.Normal,
                        overflow = TextOverflow.Ellipsis,)
                }
            }
        }
    }
}