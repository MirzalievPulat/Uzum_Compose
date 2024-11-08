package uz.gita.uzumcompose.di

//import uz.gita.presentation.auth.pin.PinContract
//import uz.gita.uzumcompose.screens.auth.pin.PinDirections
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import uz.gita.presentation.auth.enterPin.EnterPinContract
import uz.gita.presentation.auth.setPin.SetPinContract
import uz.gita.presentation.auth.signIn.SignInContract
import uz.gita.presentation.auth.signInVerify.SignInVerifyContract
import uz.gita.presentation.auth.signUp.SignUpContract
import uz.gita.presentation.auth.signUpVerify.SignUpVerifyContract
import uz.gita.presentation.auth.splash.SplashContract
import uz.gita.presentation.pages.home.HomePageContract
import uz.gita.presentation.pages.menu.MenuPageContract
import uz.gita.presentation.cards.addCard.AddCardContract
import uz.gita.presentation.cards.doneTransfer.DoneTransferContract
import uz.gita.presentation.cards.settingsCard.SettingsCardContract
import uz.gita.presentation.cards.transfer.TransferContract
import uz.gita.presentation.cards.transferFromTo.TransferFromToContract
import uz.gita.presentation.cards.updateCard.UpdateCardContract
import uz.gita.presentation.cards.verifyTransfer.VerifyTransferContract
import uz.gita.presentation.history.HistoryContract
import uz.gita.presentation.pages.transfer.TransferPageContract
import uz.gita.presentation.profile.ProfileContract
import uz.gita.uzumcompose.screens.auth.enterPin.EnterPinDirections
import uz.gita.uzumcompose.screens.auth.setPin.SetPinDirections
import uz.gita.uzumcompose.screens.auth.signIn.SignInDirections
import uz.gita.uzumcompose.screens.auth.signInVerify.SignInVerifyDirections
import uz.gita.uzumcompose.screens.auth.signUp.SignUpDirections
import uz.gita.uzumcompose.screens.auth.signUpVerify.SignUpVerifyDirections
import uz.gita.uzumcompose.screens.auth.splash.SplashDirections
import uz.gita.uzumcompose.screens.card.addCardScreen.AddCardDirections
import uz.gita.uzumcompose.screens.card.doneScreen.DoneTransferDirections
import uz.gita.uzumcompose.screens.card.settingsCard.SettingsCardDirections
import uz.gita.uzumcompose.screens.card.transferCard.TransferDirections
import uz.gita.uzumcompose.screens.card.transferFromTo.TransferFromToDirections
import uz.gita.uzumcompose.screens.card.updateCard.UpdateCardDirections
import uz.gita.uzumcompose.screens.card.verifyTransfer.VerifyTransferDirections
import uz.gita.uzumcompose.screens.history.HistoryDirections
import uz.gita.uzumcompose.screens.pages.home.HomePageDirections
import uz.gita.uzumcompose.screens.pages.menu.MenuPageDirections
import uz.gita.uzumcompose.screens.pages.transfer.TransferPageDirections
import uz.gita.uzumcompose.screens.profile.ProfileDirections


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
    fun bindSetPinDirection(impl: SetPinDirections): SetPinContract.Direction

    @[Binds ViewModelScoped]
    fun bindRePinDirection(impl: EnterPinDirections): EnterPinContract.Direction

    @[Binds ViewModelScoped]
    fun bindHomePageDirection(impl: HomePageDirections): HomePageContract.Direction

    @[Binds ViewModelScoped]
    fun bindAddCardDirection(impl: AddCardDirections): AddCardContract.Direction

    @[Binds ViewModelScoped]
    fun bindMenuPageDirection(impl: MenuPageDirections): MenuPageContract.Direction

    @[Binds ViewModelScoped]
    fun bindUpdateCardScreenDirection(impl: UpdateCardDirections): UpdateCardContract.Direction

    @[Binds ViewModelScoped]
    fun bindProfileScreenDirection(impl: ProfileDirections): ProfileContract.Direction

    @[Binds ViewModelScoped]
    fun bindTransferCardDirection(impl: TransferDirections): TransferContract.Direction

    @[Binds ViewModelScoped]
    fun bindTransferPageDirection(impl: TransferPageDirections): TransferPageContract.Direction

    @[Binds ViewModelScoped]
    fun bindTransferFromToDirection(impl: TransferFromToDirections): TransferFromToContract.Direction

    @[Binds ViewModelScoped]
    fun bindHistoryDirection(impl: HistoryDirections): HistoryContract.Direction

    @[Binds ViewModelScoped]
    fun bindVerifyTransferDirection(impl: VerifyTransferDirections): VerifyTransferContract.Direction

    @[Binds ViewModelScoped]
    fun bindDoneTransferDirection(impl: DoneTransferDirections): DoneTransferContract.Direction

    @[Binds ViewModelScoped]
    fun bindSettingsCardDirection(impl: SettingsCardDirections): SettingsCardContract.Direction

}