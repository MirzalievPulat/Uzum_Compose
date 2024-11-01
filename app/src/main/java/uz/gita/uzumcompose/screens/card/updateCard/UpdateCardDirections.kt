package uz.gita.uzumcompose.screens.card.updateCard

import uz.gita.presentation.auth.signUp.SignUpContract
import uz.gita.presentation.cards.addCard.AddCardContract
import uz.gita.presentation.cards.updateCard.UpdateCardContract
import uz.gita.uzumcompose.screens.auth.signIn.SignInScreen
import uz.gita.uzumcompose.screens.auth.signUpVerify.SignUpVerifyScreen
import uz.gita.uzumcompose.screens.main.MainScreen
import uz.gita.uzumcompose.screens.pages.home.HomePage
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class UpdateCardDirections @Inject constructor(private val appNavigator: AppNavigator) : UpdateCardContract.Direction {
    override suspend fun goBack() {
        appNavigator.back()
    }

    override suspend fun goHome() {
        appNavigator.replaceAll(MainScreen())
    }
}