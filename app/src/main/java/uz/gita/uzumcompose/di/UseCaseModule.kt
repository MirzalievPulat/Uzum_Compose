package uz.gita.uzumcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import uz.gita.uzumcompose.domain.repository.AuthRepository
import uz.gita.uzumcompose.domain.useCase.SignInResendUC
import uz.gita.uzumcompose.domain.useCase.SignInUC
import uz.gita.uzumcompose.domain.useCase.SignInVerifyUC
import uz.gita.uzumcompose.domain.useCase.SignUpResendUC
import uz.gita.uzumcompose.domain.useCase.SignUpUC
import uz.gita.uzumcompose.domain.useCase.SignUpVerifyUC
import uz.gita.uzumcompose.domain.useCase.UpdateTokenUC
import uz.gita.uzumcompose.domain.useCase.impl.SignInResendUCImpl
import uz.gita.uzumcompose.domain.useCase.impl.SignInUCImpl
import uz.gita.uzumcompose.domain.useCase.impl.SignInVerifyUCImpl
import uz.gita.uzumcompose.domain.useCase.impl.SignUpResendUCImpl
import uz.gita.uzumcompose.domain.useCase.impl.SignUpUCImpl
import uz.gita.uzumcompose.domain.useCase.impl.SignUpVerifyUCImpl
import uz.gita.uzumcompose.domain.useCase.impl.UpdateTokenUCImpl

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @[Provides ViewModelScoped]
    fun provideSignUpUC(repository: AuthRepository): SignUpUC = SignUpUCImpl(repository)
    @[Provides ViewModelScoped]
    fun provideSignInUC(repository: AuthRepository): SignInUC = SignInUCImpl(repository)
    @[Provides ViewModelScoped]
    fun provideSignUpVerifyUC(repository: AuthRepository):SignUpVerifyUC = SignUpVerifyUCImpl(repository)
    @[Provides ViewModelScoped]
    fun provideSignInVerifyUC(repository: AuthRepository): SignInVerifyUC = SignInVerifyUCImpl(repository)
    @[Provides ViewModelScoped]
    fun provideSignInResendUC(repository: AuthRepository): SignInResendUC = SignInResendUCImpl(repository)
    @[Provides ViewModelScoped]
    fun provideSignUpResendUC(repository: AuthRepository): SignUpResendUC = SignUpResendUCImpl(repository)
    @[Provides ViewModelScoped]
    fun provideUpdateTokenUC(repository: AuthRepository): UpdateTokenUC = UpdateTokenUCImpl(repository)

}