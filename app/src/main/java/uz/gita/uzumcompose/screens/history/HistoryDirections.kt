package uz.gita.uzumcompose.screens.history

import uz.gita.common.data.CardData
import uz.gita.presentation.history.HistoryContract
import uz.gita.presentation.pages.home.HomePageContract
import uz.gita.uzumcompose.screens.card.addCardScreen.AddCardScreen
import uz.gita.uzumcompose.screens.card.updateCard.UpdateCardScreen
import uz.gita.uzumcompose.screens.profile.ProfileScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class HistoryDirections @Inject constructor(private val appNavigator: AppNavigator) : HistoryContract.Direction {
    override suspend fun goBack() {
        appNavigator.back()
    }
}