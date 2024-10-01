package uz.gita.uzumcompose.screens.menu.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import uz.gita.presentation.auth.signUp.SignUpContract
import uz.gita.presentation.auth.signUp.SignUpVM
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.screens.pages.menu.PopularSection
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.TextField
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontUzumPro

class CardsScreen : Screen {
    @Composable
    override fun Content() {
        CardsScreenContent()
    }
}

@Preview
@Composable
fun CardsScreenPreview() {
    UzumComposeTheme {
        CardsScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsScreenContent() {

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