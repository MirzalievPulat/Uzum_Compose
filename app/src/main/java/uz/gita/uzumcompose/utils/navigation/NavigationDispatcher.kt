package uz.gita.uzumcompose.utils.navigation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationDispatcher @Inject constructor(): AppNavigator, NavigationHandler {
    override val navigationStack = MutableSharedFlow<NavigationArgs>()

    private suspend fun navigate(args: NavigationArgs){
        navigationStack.emit(args)
    }


    override suspend fun back() = navigate{ pop() }

    override suspend fun navigateTo(appScreen: AppScreen) = navigate{ push(appScreen)}

    override suspend fun replace(appScreen: AppScreen) = navigate{ replace(appScreen)}
    override suspend fun replaceAll(appScreen: AppScreen)  = navigate { replaceAll(appScreen) }
}