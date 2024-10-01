package uz.gita.uzumcompose.screens.auth.enterpin

import uz.gita.presentation.auth.enterPin.EnterPinContract
import uz.gita.presentation.auth.repin.RePinContract
import uz.gita.uzumcompose.screens.main.MainScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class EnterPinDirections @Inject constructor(private val appNavigator: AppNavigator) : EnterPinContract.Direction {
    override suspend fun moveToMainScreen() {
        appNavigator.replaceAll(MainScreen())
    }

//    override suspend fun moveBack() {
//        appNavigator.back()
//    }
}