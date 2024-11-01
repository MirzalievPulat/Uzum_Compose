package uz.gita.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import uz.gita.domain.homeUseCase.GetBasicInfoUC
import uz.gita.domain.homeUseCase.GetFullInfoUC
import uz.gita.domain.homeUseCase.GetIsMoneyVisibleUC
import uz.gita.domain.homeUseCase.GetLastTransfersUC
import uz.gita.domain.homeUseCase.GetTotalBalanceUC
import uz.gita.domain.homeUseCase.UpdateInfoUC
import uz.gita.domain.homeUseCase.SetIsMoneyVisibleUC
import uz.gita.domain.homeUseCase.impl.GetBasicInfoImpl
import uz.gita.domain.homeUseCase.impl.GetFullInfoImpl
import uz.gita.domain.homeUseCase.impl.GetIsMoneyVisibleUCImpl
import uz.gita.domain.homeUseCase.impl.GetLastTransfersUCImpl
import uz.gita.domain.homeUseCase.impl.GetTotalBalanceImpl
import uz.gita.domain.homeUseCase.impl.UpdateInfoImpl
import uz.gita.domain.homeUseCase.impl.SetIsMoneyVisibleUCImpl


@Module
@InstallIn(ViewModelComponent::class)
interface HomeUcModule {

    @[Binds ViewModelScoped]
    fun provideBasicInfoUC(impl: GetBasicInfoImpl): GetBasicInfoUC

    @[Binds ViewModelScoped]
    fun provideFullInfoUC(impl: GetFullInfoImpl): GetFullInfoUC

    @[Binds ViewModelScoped]
    fun provideLastTransfersUC(impl: GetLastTransfersUCImpl): GetLastTransfersUC

    @[Binds ViewModelScoped]
    fun provideTotalBalanceUC(impl: GetTotalBalanceImpl): GetTotalBalanceUC

    @[Binds ViewModelScoped]
    fun provideUpdateInfoUC(impl: UpdateInfoImpl): UpdateInfoUC

    @[Binds ViewModelScoped]
    fun provideGetIsMoneyVisibleUC(impl: GetIsMoneyVisibleUCImpl): GetIsMoneyVisibleUC

    @[Binds ViewModelScoped]
    fun provideSetIsMoneyVisibleUC(impl: SetIsMoneyVisibleUCImpl): SetIsMoneyVisibleUC


}