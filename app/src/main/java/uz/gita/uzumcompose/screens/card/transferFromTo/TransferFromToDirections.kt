package uz.gita.uzumcompose.screens.card.transferFromTo

import uz.gita.presentation.cards.transferFromTo.TransferFromToContract
import uz.gita.uzumcompose.screens.card.addCardScreen.AddCardScreen
import uz.gita.uzumcompose.screens.card.verifyTransfer.VerifyTransferScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class TransferFromToDirections @Inject constructor(private val appNavigator: AppNavigator) : TransferFromToContract.Direction {
    override suspend fun goBack() {
        appNavigator.back()
    }

    override suspend fun goVerify() {
        appNavigator.navigateTo(VerifyTransferScreen())
    }

    override suspend fun goAddCard() {
        appNavigator.navigateTo(AddCardScreen())
    }

}