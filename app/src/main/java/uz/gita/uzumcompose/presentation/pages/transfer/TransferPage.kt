package uz.gita.uzumcompose.presentation.pages.transfer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import uz.gita.uzumcompose.R

class TransferPage:Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.bottom_nav_transfer)
            val icon = rememberVectorPainter(image = ImageVector.vectorResource(R.drawable.ic_transfer))

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Text(text = "Transfer page")
    }
}