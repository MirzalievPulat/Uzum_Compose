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
import uz.gita.uzumcompose.screens.main.PolatTab
import uz.gita.uzumcompose.screens.main.PolatTabOptions

object PaymentPage:PolatTab {
    override val polatTabOptions: PolatTabOptions
        @Composable
        get() {
            val title = stringResource(R.string.bottom_nav_payment)
            val selectedIcon = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_payment_active))
            val unSelectedIcon = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_payment))

            return PolatTabOptions(
                index = 2u,
                title = title,
                selectedIcon, unSelectedIcon
            )
        }

    @Composable
    override fun Content() {
        Text(text = "Payment page")
    }
}