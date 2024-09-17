package uz.gita.uzumcompose.presentation.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme

class SearchScreen {
}

@Composable
fun SearchScreenContent() {

}

@Preview
@Composable
fun SearchScreenPreview(modifier: Modifier = Modifier) {
    UzumComposeTheme {
        Surface(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)) {

        }
    }
}