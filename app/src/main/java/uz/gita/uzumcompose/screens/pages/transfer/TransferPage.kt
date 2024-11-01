package uz.gita.uzumcompose.screens.pages.transfer

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import uz.gita.presentation.pages.transfer.TransferPageContract
import uz.gita.presentation.pages.transfer.TransferPageVM
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.screens.main.PolatTab
import uz.gita.uzumcompose.screens.main.PolatTabOptions
import uz.gita.uzumcompose.ui.components.AppTextField
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.TextField
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum

object TransferPage : PolatTab {
    private fun readResolve(): Any = TransferPage
    override val polatTabOptions: PolatTabOptions
        @Composable
        get() {
            val title = stringResource(R.string.bottom_nav_transfer)
            val selectedIcon = rememberVectorPainter(image = ImageVector.vectorResource(R.drawable.ic_transfer_active))
            val unSelectedIcon = rememberVectorPainter(image = ImageVector.vectorResource(R.drawable.ic_transfer))

            return remember {
                PolatTabOptions(
                    index = 1u,
                    title = title,
                    selectedIcon, unSelectedIcon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: TransferPageContract.ViewModel = getViewModel<TransferPageVM>()
        TransferPageContent(viewModel::onEventDispatcher)
    }
}

@Preview
@Composable
fun TransferContentPreview(modifier: Modifier = Modifier) {
    UzumComposeTheme {
        TransferPageContent({})
    }
}

@Composable
fun TransferPageContent(
    onEventDispatcher: (TransferPageContract.Intent) -> Unit
) {

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
                    .aspectRatio(5 / 1f)
                    .background(color = Color.White)
                    .padding(all = 16.dp)
                    ,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = stringResource(R.string.txt_transfer),
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
                .background(color = Color.White)
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            CardTransferSection(onEventDispatcher)
            ShapesSection()
            OptionsSection()
        }
    }
}

@Composable
fun CardTransferSection(onEventDispatcher: (TransferPageContract.Intent) -> Unit) {
    var value by remember { mutableStateOf("") }
    val navigator = LocalNavigator.current

    AppTextField(
        hint = stringResource(R.string.card_number_or_name),
        value = value,
        onValueChange = { value = it },
        enabled = false,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                Log.d("TAG", "CardTransferSection: to Transfer oynage bosildi")
                onEventDispatcher(TransferPageContract.Intent.PanTextFieldClick)
            },
        trailingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_24_scan_card),
                contentDescription = "Card scan",
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    )
}

@Composable
fun ShapesSection() {
    Row(
        modifier = Modifier
            .aspectRatio(7 / 2f)
            .background(color = Color.White)
            .horizontalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .border(
                    width = 1.dp,
                    color = Color.TextField,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
                .fillMaxHeight()
                .aspectRatio(1 / 1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.txt_before_shapes),
                color = Color.HintUzum,
                fontSize = 10.sp,
                fontFamily = fontFamilyUzum,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 12.sp
            )

            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = "Arrow",
                modifier = Modifier.size(16.dp)
            )
        }

        Image(painter = painterResource(id = R.drawable.ic_placeholder_triangle), contentDescription ="Triangle",
            modifier = Modifier.padding(start = 16.dp))
        Image(painter = painterResource(id = R.drawable.ic_placeholder_octagon), contentDescription ="Triangle",
            modifier = Modifier.padding(start = 8.dp))
        Image(painter = painterResource(id = R.drawable.ic_placeholder_big_arrow), contentDescription ="Triangle",
            modifier = Modifier.padding(start = 8.dp))
        Image(painter = painterResource(id = R.drawable.ic_placeholder_rhombus), contentDescription ="Triangle",
            modifier = Modifier.padding(start = 8.dp, end = 16.dp))
    }
}

data class Options(
    val icon:Int,
    val title:String,
    val subTitle:String,
)

val optionsList = arrayListOf(
    Options(R.drawable.ic_24_vault_line,"By account number","Within Uzbekistan"),
    Options(R.drawable.ic_budget_24,"By requisites","Transfer to payment"),
    Options(R.drawable.ic_currency_exchange_24,"Currency exchange","Purchase and sale"),
    Options(R.drawable.ic_arrow_south_west_24,"From Russia","Fast and without commission"),
    Options(R.drawable.ic_arrow_north_east_24,"To Russia","To all cards of the RF without commission"),
    Options(R.drawable.ic_westernunion_24,"Western Union","Add a card of KapitalBank"),
)

@Composable
fun OptionsSection() {
    Column(modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        optionsList.forEach{option->
            Row (modifier = Modifier.padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically){
                Box (modifier = Modifier
                    .background(
                        color = Color.TextField,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(10.dp)
                ){
                    Icon(imageVector = ImageVector.vectorResource(id =option.icon) ,
                        contentDescription = "icon")
                }

                Column (verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)){
                    Text(text = option.title,
                        color = Color.BlackUzum,
                        fontSize = 16.sp,
                        fontFamily = fontFamilyUzum,
                        fontWeight = FontWeight.Medium,
                        overflow = TextOverflow.Ellipsis,)

                    Text(text = option.subTitle,
                        color = Color.HintUzum,
                        fontSize = 14.sp,
                        maxLines = 1,
                        fontFamily = fontFamilyUzum,
                        fontWeight = FontWeight.Medium,
                        overflow = TextOverflow.Ellipsis,)
                }

                Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_right_round_arrow),
                    contentDescription = "Next",
                    tint = Color.HintUzum,
                    modifier = Modifier.size(14.dp))
            }
        }
    }
}