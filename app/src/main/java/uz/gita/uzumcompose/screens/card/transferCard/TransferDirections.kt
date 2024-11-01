package uz.gita.uzumcompose.screens.card.transferCard

import uz.gita.presentation.cards.transfer.TransferContract
import uz.gita.uzumcompose.screens.card.transferFromTo.TransferFromToScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class TransferDirections @Inject constructor(private val appNavigator: AppNavigator) : TransferContract.Direction {
    override suspend fun goBack() {
        appNavigator.back()
    }

    override suspend fun goTransferFromTo(name:String,pan:String) {
        appNavigator.navigateTo(TransferFromToScreen(name,pan))
    }

    override suspend fun goCamera() {

    }

}