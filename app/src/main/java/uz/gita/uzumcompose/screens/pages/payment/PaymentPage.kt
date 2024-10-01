package uz.gita.uzumcompose.screens.pages.payment

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import uz.gita.uzumcompose.R

class PaymentPage:Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.bottom_nav_payment)
            val icon = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_payment))

            return TabOptions(
                index = 2u,
                title = title,
                icon = icon
            )
        }

    @Composable
    override fun Content() {
        Text(text = "Payment page")
    }
}