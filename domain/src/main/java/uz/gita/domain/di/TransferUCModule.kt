package uz.gita.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import uz.gita.domain.homeUseCase.GetBasicInfoUC
import uz.gita.domain.homeUseCase.impl.GetBasicInfoImpl
import uz.gita.domain.transferUseCase.GetCardOwnerUC
import uz.gita.domain.transferUseCase.GetFeeUC
import uz.gita.domain.transferUseCase.GetHistoryPagingUC
import uz.gita.domain.transferUseCase.GetHistoryUC
import uz.gita.domain.transferUseCase.GetLastTransferredCardsUC
import uz.gita.domain.transferUseCase.GetPhoneNumberUC
import uz.gita.domain.transferUseCase.GetTransferringCardDetails
import uz.gita.domain.transferUseCase.SaveLastTransferredCardsUC
import uz.gita.domain.transferUseCase.SaveTransferringCardDetailsUC
import uz.gita.domain.transferUseCase.TransferResendUC
import uz.gita.domain.transferUseCase.TransferUC
import uz.gita.domain.transferUseCase.TransferVerifyUC
import uz.gita.domain.transferUseCase.impl.GetCardOwnerUCImpl
import uz.gita.domain.transferUseCase.impl.GetFeeUCImpl
import uz.gita.domain.transferUseCase.impl.GetHistoryPagingUCImpl
import uz.gita.domain.transferUseCase.impl.GetHistoryUCImpl
import uz.gita.domain.transferUseCase.impl.GetLastTransferredCardsUCImpl
import uz.gita.domain.transferUseCase.impl.GetPhoneNumberUCImpl
import uz.gita.domain.transferUseCase.impl.GetTransferringCardDetailsUCImpl
import uz.gita.domain.transferUseCase.impl.SaveLastTransferredCardsUCImpl
import uz.gita.domain.transferUseCase.impl.SaveTransferringCardDetailsUCImpl
import uz.gita.domain.transferUseCase.impl.TransferResendUCImpl
import uz.gita.domain.transferUseCase.impl.TransferUCImpl
import uz.gita.domain.transferUseCase.impl.TransferVerifyUCImpl

@Module
@InstallIn(ViewModelComponent::class)
interface TransferUCModule {

    @[Binds ViewModelScoped]
    fun provideGetCardOwnerUC(impl: GetCardOwnerUCImpl): GetCardOwnerUC

    @[Binds ViewModelScoped]
    fun provideGetFeeUC(impl: GetFeeUCImpl): GetFeeUC

    @[Binds ViewModelScoped]
    fun provideGetHistoryUC(impl: GetHistoryUCImpl): GetHistoryUC

    @[Binds ViewModelScoped]
    fun provideTransferResendUC(impl: TransferResendUCImpl): TransferResendUC

    @[Binds ViewModelScoped]
    fun provideTransferUC(impl: TransferUCImpl): TransferUC

    @[Binds ViewModelScoped]
    fun provideTransferVerifyUC(impl: TransferVerifyUCImpl): TransferVerifyUC

    @[Binds ViewModelScoped]
    fun provideGetHistoryPagingUC(impl: GetHistoryPagingUCImpl): GetHistoryPagingUC

    @[Binds ViewModelScoped]
    fun provideGetPhoneNumberUC(impl: GetPhoneNumberUCImpl): GetPhoneNumberUC



    @[Binds ViewModelScoped]
    fun provideGetLastTransferredCardsDetailsUC(impl: GetLastTransferredCardsUCImpl): GetLastTransferredCardsUC

    @[Binds ViewModelScoped]
    fun provideSaveLastTransferredCardsDetailsUC(impl: SaveLastTransferredCardsUCImpl): SaveLastTransferredCardsUC



    @[Binds ViewModelScoped]
    fun provideGetTransferringCardDetailsUC(impl: GetTransferringCardDetailsUCImpl): GetTransferringCardDetails

    @[Binds ViewModelScoped]
    fun provideGetSaveTransferringCardDetailsUC(impl: SaveTransferringCardDetailsUCImpl): SaveTransferringCardDetailsUC

}