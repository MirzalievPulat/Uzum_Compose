package uz.gita.uzumcompose.di

//import uz.gita.presentation.auth.pin.PinContract
//import uz.gita.uzumcompose.screens.auth.pin.PinDirections
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import uz.gita.presentation.auth.enterPin.EnterPinContract
import uz.gita.presentation.auth.pin.PinContract
import uz.gita.presentation.auth.repin.RePinContract
import uz.gita.presentation.auth.signIn.SignInContract
import uz.gita.presentation.auth.signInVerify.SignInVerifyContract
import uz.gita.presentation.auth.signUp.SignUpContract
import uz.gita.presentation.auth.signUpVerify.SignUpVerifyContract
import uz.gita.presentation.auth.splash.SplashContract
import uz.gita.presentation.home.HomePageContract
import uz.gita.presentation.menu.addCard.AddCardContract
import uz.gita.uzumcompose.screens.auth.enterpin.EnterPinDirections
import uz.gita.uzumcompose.screens.auth.pin.PinDirections
import uz.gita.uzumcompose.screens.auth.repin.RePinDirections
import uz.gita.uzumcompose.screens.auth.signIn.SignInDirections
import uz.gita.uzumcompose.screens.auth.signInVerify.SignInVerifyDirections
import uz.gita.uzumcompose.screens.auth.signUp.SignUpDirections
import uz.gita.uzumcompose.screens.auth.signUpVerify.SignUpVerifyDirections
import uz.gita.uzumcompose.screens.auth.splash.SplashDirections
import uz.gita.uzumcompose.screens.menu.card.addCardScreen.AddCardDirections
import uz.gita.uzumcompose.screens.pages.home.HomePageDirections


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
    fun bindEnterPinDirection(impl: EnterPinDirections): EnterPinContract.Direction

    @[Binds ViewModelScoped]
    fun bindRePinDirection(impl: RePinDirections): RePinContract.Direction

    @[Binds ViewModelScoped]
    fun bindHomePageDirection(impl: HomePageDirections): HomePageContract.Direction

    @[Binds ViewModelScoped]
    fun bindAddCardDirection(impl: AddCardDirections): AddCardContract.Direction
}