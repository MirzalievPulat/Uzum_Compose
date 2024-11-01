package uz.gita.uzumcompose.screens.card.verifyTransfer

import uz.gita.presentation.cards.verifyTransfer.VerifyTransferContract
import uz.gita.uzumcompose.screens.card.doneScreen.DoneTransferScreen
import uz.gita.uzumcompose.screens.card.doneScreen.DoneTransferScreenPrev
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class VerifyTransferDirections @Inject constructor(private val appNavigator: AppNavigator) : VerifyTransferContract.Direction {
    override suspend fun goBack() {
        appNavigator.back()
    }

    override suspend fun goDoneScreen() {
        appNavigator.replaceAll(DoneTransferScreen())
    }
}