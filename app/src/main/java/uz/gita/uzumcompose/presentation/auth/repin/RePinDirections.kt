package uz.gita.uzumcompose.presentation.auth.repin

import uz.gita.uzumcompose.presentation.main.MainScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class RePinDirections @Inject constructor(private val appNavigator: AppNavigator) : RePinContract.Direction {
    override suspend fun moveToMainScreen() {
        appNavigator.replaceAll(MainScreen())
    }

    override suspend fun moveBack() {
        appNavigator.back()
    }
}