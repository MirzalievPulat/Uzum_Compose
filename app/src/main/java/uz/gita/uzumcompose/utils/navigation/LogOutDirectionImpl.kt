package uz.gita.uzumcompose.utils.navigation

import uz.gita.common.direction.LogOutDirection
import uz.gita.uzumcompose.screens.auth.splash.SplashScreen
import javax.inject.Inject

class LogOutDirectionImpl @Inject constructor(private val appNavigator: AppNavigator):LogOutDirection {
    override suspend fun logOut() {
        appNavigator.replaceAll(SplashScreen())
    }
}