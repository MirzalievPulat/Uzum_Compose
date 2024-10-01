package uz.gita.uzumcompose.screens.auth.signUpVerify

import uz.gita.presentation.auth.signUpVerify.SignUpVerifyContract
import uz.gita.uzumcompose.screens.auth.pin.PinScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class SignUpVerifyDirections @Inject constructor(private val appNavigator: AppNavigator) : SignUpVerifyContract.Direction {
    override suspend fun moveToPinScreen() {
        appNavigator.replaceAll(PinScreen())
    }

    override suspend fun moveBack() {
        appNavigator.back()
    }
}