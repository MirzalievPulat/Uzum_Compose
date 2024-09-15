package uz.gita.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.gita.data.remote.api.AuthApi
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @[Provides Singleton]
    fun providesAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)
}