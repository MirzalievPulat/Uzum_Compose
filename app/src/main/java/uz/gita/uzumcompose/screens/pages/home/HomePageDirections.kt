package uz.gita.uzumcompose.screens.pages.home

import uz.gita.presentation.home.home.HomePageContract
import uz.gita.uzumcompose.screens.menu.card.addCardScreen.AddCardScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class HomePageDirections @Inject constructor(private val appNavigator: AppNavigator) : HomePageContract.Direction {
    override suspend fun goAddCardScreen() {
        appNavigator.navigateTo(AddCardScreen())
    }

}