package uz.gita.uzumcompose.presentation.splash

import uz.gita.uzumcompose.presentation.pin.PinScreen
import uz.gita.uzumcompose.presentation.signIn.SignInScreen
import uz.gita.uzumcompose.presentation.signUp.SignUpScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class SplashDirections @Inject constructor(val appNavigator: AppNavigator):SplashContract.Direction {
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