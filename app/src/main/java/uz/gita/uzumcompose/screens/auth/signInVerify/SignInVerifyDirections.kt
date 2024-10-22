package uz.gita.uzumcompose.screens.auth.signInVerify

import uz.gita.presentation.auth.signInVerify.SignInVerifyContract
import uz.gita.uzumcompose.screens.auth.setPin.SetPinScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class SignInVerifyDirections @Inject constructor(private val appNavigator: AppNavigator) : SignInVerifyContract.Direction {
    override suspend fun moveToPinScreen() {
        appNavigator.replaceAll(SetPinScreen())
    }

    override suspend fun moveBack() {
        appNavigator.back()
    }
}