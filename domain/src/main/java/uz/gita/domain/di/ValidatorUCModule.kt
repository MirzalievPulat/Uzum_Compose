package uz.gita.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import uz.gita.domain.validatorUseCase.BirthDateValidatorUC
import uz.gita.domain.validatorUseCase.CardPanValidatorUC
import uz.gita.domain.validatorUseCase.FirstNameValidatorUC
import uz.gita.domain.validatorUseCase.LastNameValidatorUC
import uz.gita.domain.validatorUseCase.PasswordValidatorUC
import uz.gita.domain.validatorUseCase.PhoneNumberValidatorUC
import uz.gita.domain.validatorUseCase.YearValidatorUC
import uz.gita.domain.validatorUseCase.impl.BirthDateValidatorUCImpl
import uz.gita.domain.validatorUseCase.impl.CardPanValidatorUCImpl
import uz.gita.domain.validatorUseCase.impl.FirstNameValidatorUCImpl
import uz.gita.domain.validatorUseCase.impl.LastNameValidatorUCImpl
import uz.gita.domain.validatorUseCase.impl.PasswordValidatorUCImpl
import uz.gita.domain.validatorUseCase.impl.PhoneNumberValidatorUCImpl
import uz.gita.domain.validatorUseCase.impl.YearValidatorUCImpl

@Module
@InstallIn(ViewModelComponent::class)
interface ValidatorUCModule {
    @[Binds ViewModelScoped]
    fun bindFirsNameValidatorUC(impl:FirstNameValidatorUCImpl):FirstNameValidatorUC

    @[Binds ViewModelScoped]
    fun bindLastNameValidatorUC(impl: LastNameValidatorUCImpl): LastNameValidatorUC

    @[Binds ViewModelScoped]
    fun bindPhoneNumberValidatorUC(impl:PhoneNumberValidatorUCImpl):PhoneNumberValidatorUC

    @[Binds ViewModelScoped]
    fun bindPasswordValidatorUC(impl:PasswordValidatorUCImpl):PasswordValidatorUC

    @[Binds ViewModelScoped]
    fun bindBirthDateValidatorUC(impl: BirthDateValidatorUCImpl): BirthDateValidatorUC

    @[Binds ViewModelScoped]
    fun bindCardPanValidatorUC(impl: CardPanValidatorUCImpl): CardPanValidatorUC

    @[Binds ViewModelScoped]
    fun bindYearValidatorUC(impl: YearValidatorUCImpl): YearValidatorUC


}