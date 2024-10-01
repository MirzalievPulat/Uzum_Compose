package uz.gita.uzumcompose.screens.auth.pin

import uz.gita.presentation.auth.pin.PinContract
import uz.gita.uzumcompose.screens.auth.repin.RePinScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class PinDirections @Inject constructor(private val appNavigator: AppNavigator) :
    PinContract.Direction {
    override suspend fun moveToRePinScreen(code: String) {
        appNavigator.navigateTo(RePinScreen(code))
    }

}