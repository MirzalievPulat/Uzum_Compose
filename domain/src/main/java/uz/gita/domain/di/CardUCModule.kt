package uz.gita.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import uz.gita.domain.authUseCase.SignUpUC
import uz.gita.domain.authUseCase.impl.SignUpUCImpl
import uz.gita.domain.cardUseCase.AddCardUC
import uz.gita.domain.cardUseCase.DeleteCardUC
import uz.gita.domain.cardUseCase.GetCardsUC
import uz.gita.domain.cardUseCase.UpdateCardUC
import uz.gita.domain.cardUseCase.impl.AddCardUCImpl
import uz.gita.domain.cardUseCase.impl.DeleteCardUCImpl
import uz.gita.domain.cardUseCase.impl.GetCardsUCImpl
import uz.gita.domain.cardUseCase.impl.UpdateCardUCImpl

@Module
@InstallIn(ViewModelComponent::class)
interface CardUCModule {

    @[Binds ViewModelScoped]
    fun provideAddCardUC(impl: AddCardUCImpl): AddCardUC

     @[Binds ViewModelScoped]
    fun provideDeleteCardUC(impl: DeleteCardUCImpl): DeleteCardUC

    @[Binds ViewModelScoped]
    fun provideGetCardUC(impl: GetCardsUCImpl): GetCardsUC

    @[Binds ViewModelScoped]
    fun provideUpdateCardUC(impl: UpdateCardUCImpl): UpdateCardUC

}