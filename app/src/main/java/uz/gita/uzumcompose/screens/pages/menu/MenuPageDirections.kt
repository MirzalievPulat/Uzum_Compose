package uz.gita.uzumcompose.screens.pages.menu

import uz.gita.presentation.pages.menu.MenuPageContract
import uz.gita.uzumcompose.screens.card.addCardScreen.AddCardScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class MenuPageDirections @Inject constructor(private val appNavigator: AppNavigator) : MenuPageContract.Direction {
    override suspend fun goAddCardScreen() {
        appNavigator.navigateTo(AddCardScreen())
    }

}