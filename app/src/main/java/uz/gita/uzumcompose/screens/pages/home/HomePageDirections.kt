package uz.gita.uzumcompose.screens.pages.home

import uz.gita.common.data.CardData
import uz.gita.presentation.pages.home.HomePageContract
import uz.gita.uzumcompose.screens.card.addCardScreen.AddCardScreen
import uz.gita.uzumcompose.screens.card.updateCard.UpdateCardScreen
import uz.gita.uzumcompose.screens.history.HistoryScreen
import uz.gita.uzumcompose.screens.profile.ProfileScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class HomePageDirections @Inject constructor(private val appNavigator: AppNavigator) : HomePageContract.Direction {

    override suspend fun goUpdateCard(cardInfo: CardData.CardInfo) {
        appNavigator.navigateTo(UpdateCardScreen(cardInfo))
    }

    override suspend fun goProfileScreen() {
        appNavigator.navigateTo(ProfileScreen())
    }

    override suspend fun goMonitoring() {
        appNavigator.navigateTo(HistoryScreen())
    }
}