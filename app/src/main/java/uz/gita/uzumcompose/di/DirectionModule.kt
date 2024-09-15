package uz.gita.uzumcompose.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import uz.gita.uzumcompose.presentation.auth.pin.PinContract
import uz.gita.uzumcompose.presentation.auth.pin.PinDirections
import uz.gita.uzumcompose.presentation.auth.repin.RePinContract
import uz.gita.uzumcompose.presentation.auth.repin.RePinDirections
import uz.gita.uzumcompose.presentation.auth.signIn.SignInContract
import uz.gita.uzumcompose.presentation.auth.signIn.SignInDirections
import uz.gita.uzumcompose.presentation.auth.signInVerify.SignInVerifyContract
import uz.gita.uzumcompose.presentation.auth.signInVerify.SignInVerifyDirections
import uz.gita.uzumcompose.presentation.auth.signUp.SignUpContract
import uz.gita.uzumcompose.presentation.auth.signUp.SignUpDirections
import uz.gita.uzumcompose.presentation.auth.signUpVerify.SignUpVerifyContract
import uz.gita.uzumcompose.presentation.auth.signUpVerify.SignUpVerifyDirections
import uz.gita.uzumcompose.presentation.auth.splash.SplashContract
import uz.gita.uzumcompose.presentation.auth.splash.SplashDirections


@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {

    @[Binds ViewModelScoped]
    fun bindSplashDirection(impl: SplashDirections): SplashContract.Direction

    @[Binds ViewModelScoped]
    fun bindSignUpDirection(impl: SignUpDirections): SignUpContract.Direction

    @[Binds ViewModelScoped]
    fun bindSignInDirection(impl: SignInDirections): SignInContract.Direction

    @[Binds ViewModelScoped]
    fun bindSignUpVerifyDirection(impl: SignUpVerifyDirections): SignUpVerifyContract.Direction

    @[Binds ViewModelScoped]
    fun bindSignInVerifyDirection(impl: SignInVerifyDirections): SignInVerifyContract.Direction

    @[Binds ViewModelScoped]
    fun bindPinDirection(impl: PinDirections): PinContract.Direction

    @[Binds ViewModelScoped]
    fun bindRePinDirection(impl: RePinDirections): RePinContract.Direction
}