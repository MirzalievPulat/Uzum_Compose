package uz.gita.uzumcompose.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme

class MainScreen : Screen {
    @Composable
    override fun Content() {

    }
}

@Preview
@Composable
fun MainScreenPreview() {
    UzumComposeTheme {
        MainScreenContent()
    }
}

@Composable
fun MainScreenContent() {

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
    }


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = "Main Screen", fontSize = 24.sp)
        }

    }
}