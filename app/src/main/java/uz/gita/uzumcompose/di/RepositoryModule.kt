package uz.gita.uzumcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.uzumcompose.data.locale.LocalStorage
import uz.gita.uzumcompose.data.remote.api.AuthApi
import uz.gita.uzumcompose.data.repository.AuthRepositoryImpl
import uz.gita.uzumcompose.domain.repository.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi, localStorage: LocalStorage): AuthRepository = AuthRepositoryImpl(api, localStorage)
}