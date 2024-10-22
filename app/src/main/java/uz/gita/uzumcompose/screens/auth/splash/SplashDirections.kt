package uz.gita.uzumcompose.screens.auth.splash

import uz.gita.presentation.auth.splash.SplashContract
import uz.gita.uzumcompose.screens.auth.enterPin.EnterPinScreen
import uz.gita.uzumcompose.screens.auth.signIn.SignInScreen
import uz.gita.uzumcompose.screens.auth.signUp.SignUpScreen

import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class SplashDirections @Inject constructor(private val appNavigator: AppNavigator) : SplashContract.Direction {
    override suspend fun moveToSignUp() {
        appNavigator.replace(SignUpScreen())
    }

    override suspend fun moveToSignIn() {
        appNavigator.replace(SignInScreen())
    }

    override suspend fun moveToPin() {
        appNavigator.replace(EnterPinScreen())
    }
}