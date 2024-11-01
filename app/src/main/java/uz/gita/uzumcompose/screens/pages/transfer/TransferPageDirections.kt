package uz.gita.uzumcompose.screens.pages.transfer

import uz.gita.presentation.pages.transfer.TransferPageContract
import uz.gita.uzumcompose.screens.card.transferCard.TransferScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class TransferPageDirections @Inject constructor(private val appNavigator: AppNavigator) : TransferPageContract.Direction {
    override suspend fun goTransferCardScreen() {
        appNavigator.navigateTo(TransferScreen())
    }
}