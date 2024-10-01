package uz.gita.uzumcompose.screens.auth.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.presentation.auth.splash.SplashVM
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme

class SplashScreen:Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<SplashVM>()

        SplashScreenContent()
    }
}

@Preview
@Composable
fun SplashScreenPreview(){
    UzumComposeTheme {
        SplashScreenContent()
    }
}

@Composable
fun SplashScreenContent(){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_uzum),
            contentDescription = "Uzum bank logo",
            modifier = Modifier
                .padding(horizontal = 64.dp)
        )
    }
}