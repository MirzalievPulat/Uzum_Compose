package uz.gita.uzumcompose.presentation.pin

import uz.gita.uzumcompose.presentation.pin.PinScreen
import uz.gita.uzumcompose.presentation.repin.RePinScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class PinDirections @Inject constructor(private val appNavigator: AppNavigator): PinContract.Direction {
    override suspend fun moveToRePinScreen(code:String) {
        appNavigator.navigateTo(RePinScreen(code))
    }

}