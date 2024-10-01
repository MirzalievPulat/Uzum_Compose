package uz.gita.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.data.repository.AuthRepository
import uz.gita.data.repository.AuthRepositoryImpl
import uz.gita.data.repository.CardRepository
import uz.gita.data.repository.CardRepositoryImpl
import uz.gita.data.repository.HomeRepository
import uz.gita.data.repository.HomeRepositoryImpl
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
}