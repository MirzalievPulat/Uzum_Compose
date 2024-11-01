package uz.gita.entity.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.gita.entity.remote.api.AuthApi
import uz.gita.entity.remote.api.CardApi
import uz.gita.entity.remote.api.HomeApi
import uz.gita.entity.remote.api.TransferApi
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

    @[Provides Singleton]
    fun providesTransferApi(retrofit: Retrofit):TransferApi = retrofit.create(TransferApi::class.java)
}