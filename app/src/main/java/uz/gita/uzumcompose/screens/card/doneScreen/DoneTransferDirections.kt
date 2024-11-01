package uz.gita.uzumcompose.screens.card.doneScreen

import uz.gita.presentation.cards.doneTransfer.DoneTransferContract
import uz.gita.uzumcompose.screens.main.MainScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class DoneTransferDirections @Inject constructor(private val appNavigator: AppNavigator) : DoneTransferContract.Direction {
    override suspend fun goMainScreen() {
        appNavigator.replaceAll(MainScreen())
    }
}