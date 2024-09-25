package uz.gita.uzumcompose.presentation.pages.menu

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import uz.gita.uzumcompose.R

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
        Text(text = "Menu page")
    }
}