package uz.gita.uzumcompose.presentation.auth.splash

import uz.gita.uzumcompose.presentation.auth.pin.PinScreen
import uz.gita.uzumcompose.presentation.auth.signIn.SignInScreen
import uz.gita.uzumcompose.presentation.auth.signUp.SignUpScreen

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
        appNavigator.replace(PinScreen())
    }
}