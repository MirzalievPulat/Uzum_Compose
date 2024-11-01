package uz.gita.entity.di

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.entity.extension.AuthAuthenticator
import uz.gita.entity.locale.LocalStorage
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @[Provides Singleton]
    fun providesOkHttpClient(
        @ApplicationContext context: Context,
        authAuthenticator: AuthAuthenticator,
        localStorage: LocalStorage
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
            .authenticator(authAuthenticator)
            .addInterceptor {
                val token = localStorage.accessToken
                val newRequest = it.request().newBuilder()
                newRequest.header("Authorization", "Bearer $token")
                it.proceed(newRequest.build())
            }
            .build()

    @[Provides Singleton]
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("http://195.158.16.140/mobile-bank/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @[Provides Singleton]
    @Named("NoAuthenticator")
    fun providesRetrofitNoAuth(): Retrofit = Retrofit.Builder()
        .baseUrl("http://195.158.16.140/mobile-bank/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}