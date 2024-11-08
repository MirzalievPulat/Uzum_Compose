package uz.gita.uzumcompose.screens.card.settingsCard

import uz.gita.presentation.cards.doneTransfer.DoneTransferContract
import uz.gita.presentation.cards.settingsCard.SettingsCardContract
import uz.gita.uzumcompose.screens.card.addCardScreen.AddCardScreen
import uz.gita.uzumcompose.screens.main.MainScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class SettingsCardDirections @Inject constructor(private val appNavigator: AppNavigator) : SettingsCardContract.Direction {
    override suspend fun goBack() {
        appNavigator.back()
    }

    override suspend fun goAddCard() {
        appNavigator.navigateTo(AddCardScreen())
    }

}