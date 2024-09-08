package uz.gita.uzumcompose.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen
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

}