package uz.gita.entity.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.entity.repository.AuthRepository
import uz.gita.entity.repository.AuthRepositoryImpl
import uz.gita.entity.repository.CardRepository
import uz.gita.entity.repository.CardRepositoryImpl
import uz.gita.entity.repository.HomeRepository
import uz.gita.entity.repository.HomeRepositoryImpl
import uz.gita.entity.repository.TransferRepository
import uz.gita.entity.repository.TransferRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun provideHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    @Singleton
    fun provideCardRepository(cardRepositoryImpl: CardRepositoryImpl): CardRepository

    @[Binds Singleton]
    fun getTransferRepository(transferRepositoryImpl: TransferRepositoryImpl):TransferRepository
}