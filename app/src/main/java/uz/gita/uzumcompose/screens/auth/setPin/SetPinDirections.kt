package uz.gita.uzumcompose.screens.auth.setPin

import uz.gita.presentation.auth.setPin.SetPinContract
import uz.gita.uzumcompose.screens.main.MainScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class SetPinDirections @Inject constructor(private val appNavigator: AppNavigator):SetPinContract.Direction {
    override suspend fun moveToMainScreen() {
        appNavigator.replaceAll(MainScreen())
    }
}