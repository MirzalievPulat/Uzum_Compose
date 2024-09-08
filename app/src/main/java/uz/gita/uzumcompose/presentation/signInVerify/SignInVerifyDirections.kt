package uz.gita.uzumcompose.presentation.signInVerify

import uz.gita.uzumcompose.presentation.pin.PinScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class SignInVerifyDirections @Inject constructor(private val appNavigator: AppNavigator): SignInVerifyContract.Direction {
    override suspend fun moveToPinScreen() {
        appNavigator.navigateTo(PinScreen())
    }

    override suspend fun moveBack() {
        appNavigator.back()
    }
}