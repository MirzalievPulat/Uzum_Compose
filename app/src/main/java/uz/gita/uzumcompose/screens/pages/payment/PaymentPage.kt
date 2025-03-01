package uz.gita.uzumcompose.screens.pages.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.screens.main.PolatTab
import uz.gita.uzumcompose.screens.main.PolatTabOptions
import uz.gita.uzumcompose.screens.pages.home.LocalPaymentSection
import uz.gita.uzumcompose.screens.pages.home.SavedPaymentsSection
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.PinkUzumPlain
import uz.gita.uzumcompose.ui.theme.TextField
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.ui.theme.fontUzumPro

object PaymentPage : PolatTab {
    private fun readResolve(): Any = PaymentPage
    override val polatTabOptions: PolatTabOptions
        @Composable
        get() {
            val title = stringResource(R.string.bottom_nav_payment)
            val selectedIcon = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_payment_active))
            val unSelectedIcon = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_payment))

            return PolatTabOptions(
                index = 2u,
                title = title,
                selectedIcon, unSelectedIcon
            )
        }

    @Composable
    override fun Content() {
        UzumComposeTheme {
            PaymentContent()
        }
    }
}

@Preview
@Composable
private fun PaymentContentPrev() {
    UzumComposeTheme {
        PaymentContent()
    }
}

@Composable
private fun PaymentContent() {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(5 / 1f)
                    .background(color = Color.White)
                    .padding(all = 16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = stringResource(R.string.bottom_nav_payment),
                    color = Color.BlackUzum,
                    fontSize = 24.sp,
                    fontFamily = fontFamilyUzum,
                    fontWeight = FontWeight.SemiBold,
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
            Spacer(modifier = Modifier.height(32.dp))
            SavedPaymentsSection()
            Spacer(modifier = Modifier.height(18.dp))
            LocalPaymentSection()
            Spacer(modifier = Modifier.height(18.dp))
            MyHomesSection()
            Spacer(modifier = Modifier.height(18.dp))
            PaymentCategory()
        }
    }
}

@Composable
private fun PaymentCategory() {
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
                text = stringResource(R.string.myHomes),
                style = TextStyle(
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.HintUzum
                ),
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_grid_view_24),
                contentDescription = "More",
                tint = Color.PinkUzum,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White, shape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            PaymentItems(name = stringResource(id = R.string.network), icon = R.drawable.ic_phone, number = 11)
            PaymentItems(name = stringResource(id = R.string.restaurants), icon = R.drawable.ic_my_home, number = 1286)
            PaymentItems(name = stringResource(id = R.string.market), icon = R.drawable.ic_home_24, number = 3942)
            PaymentItems(name = stringResource(id = R.string.utilities), icon = R.drawable.cashback_icon, number = 32)
            PaymentItems(name = stringResource(id = R.string.medicine), icon = R.drawable.ic_add, number = 570)
            PaymentItems(name = stringResource(id = R.string.providers), icon = R.drawable.ic_wifi, number = 57)
            PaymentItems(name = stringResource(id = R.string.education), icon = R.drawable.ic_public_offer, number = 584)
            PaymentItems(name = stringResource(id = R.string.entertainment), icon = R.drawable.ic_magic_stick, number = 210)
            PaymentItems(name = stringResource(id = R.string.transport), icon = R.drawable.ic_bus, number = 178)
            PaymentItems(name = stringResource(id = R.string.tv), icon = R.drawable.open_eye, number = 27)
            PaymentItems(name = stringResource(id = R.string.telephony), icon = R.drawable.ic_contact_us, number = 20)
            PaymentItems(name = stringResource(id = R.string.budget), icon = R.drawable.ic_budget_24, number = 47)
            PaymentItems(name = stringResource(id = R.string.loans), icon = R.drawable.ic_card_new_icon, number = 73)
            PaymentItems(name = stringResource(id = R.string.sports), icon = R.drawable.ic_history_menu, number = 124)
            PaymentItems(name = stringResource(id = R.string.tourism), icon = R.drawable.ic_telegram_24, number = 187)
            PaymentItems(name = stringResource(id = R.string.insurance), icon = R.drawable.ic_insurance, number = 148)
            PaymentItems(name = stringResource(id = R.string.charity), icon = R.drawable.ic_favorite_24, number = 164)
            PaymentItems(name = stringResource(id = R.string.games), icon = R.drawable.ic_star_checked, number = 46)
            PaymentItems(name = stringResource(id = R.string.services), icon = R.drawable.ic_services, number = 154)
            PaymentItems(name = stringResource(id = R.string.oriflame), icon = R.drawable.ic_nasiya_line_24, number = 1)
            PaymentItems(name = stringResource(id = R.string.advocacy), icon = R.drawable.ic_budget_24, number = 17)
            PaymentItems(name = stringResource(id = R.string.mail), icon = R.drawable.ic_two_arrow, number = 131)
            PaymentItems(name = stringResource(id = R.string.housing), icon = R.drawable.ic_my_home_active, number = 9)
            PaymentItems(name = stringResource(id = R.string.hostings), icon = R.drawable.ic_change_lang, number = 7)
            PaymentItems(name = stringResource(id = R.string.coworking), icon = R.drawable.ic_change_password_new, number = 4)
            PaymentItems(name = stringResource(id = R.string.brokers), icon = R.drawable.ic_person, number = 30)
            PaymentItems(name = stringResource(id = R.string.others), icon = R.drawable.ic_three_dots_horizontal, number = 1330)
        }
    }
}

@Composable
private fun PaymentItems(name: String, icon: Int, number: Int) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,

            modifier = Modifier
                .size(36.dp)
                .background(
                    color = Color.TextField,
                    shape = RoundedCornerShape(size = 12.dp)
                )
                .padding(8.dp),
            tint = Color.Black,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = name,
            style = TextStyle(
                fontFamily = fontFamilyUzum,
                fontSize = 16.sp,
                color = Color.BlackUzum
            ),
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = number.toString(),
            style = TextStyle(
                fontFamily = fontUzumPro,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color.HintUzum
            ),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_right_round_arrow),
            contentDescription = "More",
            modifier = Modifier.size(14.dp)
        )

    }

}

@Composable
private fun MyHomesSection() {
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
                text = stringResource(R.string.myHomes),
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
            items(2) { item ->
                Column(
                    modifier = Modifier
                        .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                        .width(120.dp)
                        .height(120.dp)
                        .padding(16.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .background(color = Color.PinkUzumPlain.copy(alpha = 0.8f), shape = RoundedCornerShape(12.dp))
                            .size(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_my_home_active),
                            contentDescription = "Icon",
                            tint = Color.PinkUzum,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Text(
                        text = if (item == 0) "Home 1" else "Home 2",
                        color = Color.BlackUzum,
                        fontSize = 16.sp,
                        fontFamily = fontFamilyUzum,
                        fontWeight = FontWeight.Medium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 18.sp,
                    )
                }
            }
        }
    }
}


