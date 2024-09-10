package uz.gita.uzumcompose.utils.navigation

import cafe.adriel.voyager.core.screen.Screen

typealias AppScreen = Screen

interface AppNavigator {
    suspend fun back()
    suspend fun navigateTo(appScreen: AppScreen)
    suspend fun replace(appScreen: AppScreen)
    suspend fun replaceAll(appScreen: AppScreen)
}