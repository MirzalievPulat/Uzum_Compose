package uz.gita.uzumcompose.screens.menu.card.addCardScreen

import uz.gita.presentation.auth.signUp.SignUpContract
import uz.gita.presentation.menu.addCard.AddCardContract
import uz.gita.uzumcompose.screens.auth.signIn.SignInScreen
import uz.gita.uzumcompose.screens.auth.signUpVerify.SignUpVerifyScreen
import uz.gita.uzumcompose.screens.main.MainScreen
import uz.gita.uzumcompose.screens.pages.home.HomePage
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class AddCardDirections @Inject constructor(private val appNavigator: AppNavigator) : AddCardContract.Direction {
    override suspend fun goBack() {
        appNavigator.back()
    }

    override suspend fun goHome() {
        appNavigator.navigateTo(MainScreen())
    }
}