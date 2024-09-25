package uz.gita.uzumcompose.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import uz.gita.uzumcompose.presentation.pages.home.HomePage
import uz.gita.uzumcompose.presentation.pages.menu.MenuPage
import uz.gita.uzumcompose.presentation.pages.message.MessagePage
import uz.gita.uzumcompose.presentation.pages.payment.PaymentPage
import uz.gita.uzumcompose.presentation.pages.transfer.TransferPage
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum

class MainScreen : Screen {
    @Composable
    override fun Content() {
        UzumComposeTheme {
            MainScreenContent()
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    UzumComposeTheme {
        MainScreenContent()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenContent() {

    TabNavigator(tab = HomePage()) {
        Scaffold(
            content = {
                CurrentTab()
            },
            bottomBar = {
                NavigationBar(modifier = Modifier
                    .defaultMinSize(minHeight = 56.dp)
                    .background(color = Color.White)
                    , containerColor = Color.White
                ) {
                    TabNavigationItem(HomePage())
                    TabNavigationItem(TransferPage())
                    TabNavigationItem(PaymentPage())
                    TabNavigationItem(MessagePage())
                    TabNavigationItem(MenuPage())
                }
            }
        )
    }
}

@Composable
fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    val interactionSource = remember { MutableInteractionSource() }


    Column(
        modifier = Modifier
            .height(56.dp)
            .weight(1f)
            .padding(top = 6.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { tabNavigator.current = tab }
            ),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = tab.options.icon!!,
            contentDescription = tab.options.title,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = tab.options.title,
            fontSize = 10.sp,
            fontFamily = fontFamilyUzum,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 18.sp
        )
    }


//    NavigationBarItem(
//        selected = tabNavigator.current == tab,
//        onClick = { tabNavigator.current = tab },
//        label = {
//            Text(
//                text = tab.options.title,
//                fontSize = 10.sp,
//                fontFamily = fontFamilyUzum,
//                fontWeight = FontWeight.SemiBold
//            )
//        },
//        icon = {
//            Icon(
//                painter = tab.options.icon!!,
//                contentDescription = tab.options.title
//            )
//        },
//        colors = NavigationBarItemDefaults.colors(
//            indicatorColor = Color.Transparent,
//        ),
//        modifier = Modifier.padding(vertical = 0.dp)
//    )
}