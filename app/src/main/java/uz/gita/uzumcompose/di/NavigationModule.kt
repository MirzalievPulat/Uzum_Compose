package uz.gita.uzumcompose.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import uz.gita.uzumcompose.utils.navigation.NavigationDispatcher
import uz.gita.uzumcompose.utils.navigation.NavigationHandler


@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    fun providesAppNavigator(dispatcher: NavigationDispatcher): AppNavigator

    @Binds
    fun providesNavigationHandler(dispatcher: NavigationDispatcher): NavigationHandler
}