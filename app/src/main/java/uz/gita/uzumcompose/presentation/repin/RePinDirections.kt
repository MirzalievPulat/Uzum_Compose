package uz.gita.uzumcompose.presentation.repin

import uz.gita.uzumcompose.presentation.main.MainScreen
import uz.gita.uzumcompose.presentation.pin.PinContract
import uz.gita.uzumcompose.presentation.pin.PinScreen
import uz.gita.uzumcompose.presentation.repin.RePinScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class RePinDirections @Inject constructor(private val appNavigator: AppNavigator): RePinContract.Direction {
    override suspend fun moveToMainScreen() {
        appNavigator.navigateTo(MainScreen())
    }

    override suspend fun moveBack() {
        appNavigator.back()
    }
}