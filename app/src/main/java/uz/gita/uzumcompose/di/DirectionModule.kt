package uz.gita.uzumcompose.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import uz.gita.uzumcompose.presentation.splash.SplashContract
import uz.gita.uzumcompose.presentation.splash.SplashDirections

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {

    @[Binds ViewModelScoped]
    fun bindSignUpDirection(impl: SplashDirections): SplashContract.Direction
}