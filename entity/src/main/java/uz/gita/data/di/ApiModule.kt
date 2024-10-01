package uz.gita.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.gita.data.remote.api.AuthApi
import uz.gita.data.remote.api.CardApi
import uz.gita.data.remote.api.HomeApi
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @[Provides Singleton]
    fun providesAuthApi(@Named("NoAuthenticator") retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @[Provides Singleton]
    fun providesHomeApi(retrofit: Retrofit):HomeApi = retrofit.create(HomeApi::class.java)

    @[Provides Singleton]
    fun providesCardApi(retrofit: Retrofit):CardApi = retrofit.create(CardApi::class.java)


}