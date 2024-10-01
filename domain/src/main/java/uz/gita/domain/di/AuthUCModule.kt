package uz.gita.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import uz.gita.domain.authUseCase.GetNextScreenUC
import uz.gita.domain.authUseCase.SetPinUC
import uz.gita.domain.authUseCase.SignInResendUC
import uz.gita.domain.authUseCase.SignInUC
import uz.gita.domain.authUseCase.SignInVerifyUC
import uz.gita.domain.authUseCase.SignUpResendUC
import uz.gita.domain.authUseCase.SignUpUC
import uz.gita.domain.authUseCase.SignUpVerifyUC
import uz.gita.domain.authUseCase.UpdateTokenUC
import uz.gita.domain.authUseCase.impl.GetNextScreenUCImpl
import uz.gita.domain.authUseCase.impl.SetPinUCImpl
import uz.gita.domain.authUseCase.impl.SignInResendUCImpl
import uz.gita.domain.authUseCase.impl.SignInUCImpl
import uz.gita.domain.authUseCase.impl.SignInVerifyUCImpl
import uz.gita.domain.authUseCase.impl.SignUpResendUCImpl
import uz.gita.domain.authUseCase.impl.SignUpUCImpl
import uz.gita.domain.authUseCase.impl.SignUpVerifyUCImpl
import uz.gita.domain.authUseCase.impl.UpdateTokenUCImpl

@Module
@InstallIn(ViewModelComponent::class)
interface AuthUCModule {

    @[Binds ViewModelScoped]
    fun provideSignUpUC(impl: SignUpUCImpl): SignUpUC

    @[Binds ViewModelScoped]
    fun provideSignInUC(impl: SignInUCImpl): SignInUC

    @[Binds ViewModelScoped]
    fun provideSignUpVerifyUC(impl: SignUpVerifyUCImpl): SignUpVerifyUC

    @[Binds ViewModelScoped]
    fun provideSignInVerifyUC(impl: SignInVerifyUCImpl): SignInVerifyUC

    @[Binds ViewModelScoped]
    fun provideSignInResendUC(impl: SignInResendUCImpl): SignInResendUC


    @[Binds ViewModelScoped]
    fun provideSignUpResendUC(impl: SignUpResendUCImpl): SignUpResendUC

    @[Binds ViewModelScoped]
    fun provideUpdateTokenUC(impl: UpdateTokenUCImpl): UpdateTokenUC

    @[Binds ViewModelScoped]
    fun provideGetNextScreenUC(impl: GetNextScreenUCImpl): GetNextScreenUC

    @[Binds ViewModelScoped]
    fun provideSetPinUC(impl: SetPinUCImpl): SetPinUC
}