package uz.gita.uzumcompose.presentation.pages.message

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

class MessagePage:Tab {
    override val options: TabOptions
        @Composable
        get(){
            val title = stringResource(R.string.bottom_nav_messages)
            val icon = rememberVectorPainter(image = ImageVector.vectorResource(R.drawable.ic_message))

            return TabOptions(
                index = 3u,
                title = title,
                icon = icon
            )
        }

    @Composable
    override fun Content() {
        Text(text = "Messages page")
    }
}