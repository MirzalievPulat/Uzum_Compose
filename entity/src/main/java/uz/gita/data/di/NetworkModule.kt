package uz.gita.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.data.extension.AuthAuthenticator
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @[Provides Singleton]
    fun providesOkHttpClient(@ApplicationContext context: Context, authAuthenticator: AuthAuthenticator):OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
            .authenticator(authAuthenticator)
/*            .addInterceptor {
                val request = it.request()
                val response = it.proceed(request)
                val finalResponse = if (response.code == 401){
                    val token = ""


                    val newRequest = request.newBuilder()
                        .header("token", token)
                        .build()

                    it.proceed(newRequest)
                } else{
                    response
                }
                finalResponse
            }*/
            .build()

    @[Provides Singleton]
    fun providesRetrofit(okHttpClient: OkHttpClient):Retrofit = Retrofit.Builder()
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