package uz.gita.uzumcompose.di

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
    fun providesAuthApi(retrofit: Retrofit): uz.gita.data.remote.api.AuthApi = retrofit.create(uz.gita.data.remote.api.AuthApi::class.java)
}