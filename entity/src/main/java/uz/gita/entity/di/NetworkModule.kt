package uz.gita.entity.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.entity.extension.AuthAuthenticator
import uz.gita.entity.extension.addLocalCacheInterceptor
import uz.gita.entity.locale.LocalStorage
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val HEADER_CACHE_CONTROL = "Cache-Control"
    private val HEADER_PRAGMA = "Pragma"
    private val cacheControl = CacheControl.Builder().maxAge(5, TimeUnit.SECONDS).build()

    @[Provides Singleton]
    fun providesOkHttpClient(
        @ApplicationContext context: Context,
        authAuthenticator: AuthAuthenticator,
        localStorage: LocalStorage
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(Cache(File(context.cacheDir, "httpCache"), (5 * 1024 * 1024).toLong()))
        .authenticator(authAuthenticator)
        .addInterceptor {
            val token = localStorage.accessToken
            val newRequest = it.request().newBuilder()
            newRequest.header("Authorization", "Bearer $token")
            it.proceed(newRequest.build())
        }
        .addLocalCacheInterceptor(
            cache = Cache(File(context.cacheDir, "httpCache"), (5 * 1024 * 1024).toLong()),
            maxAge = 5,
            maxStale = 7,
            timeUnitForStale = TimeUnit.DAYS
        )

//        .addInterceptor {
//            val request = it.request()
//            val isCaching = request.url.queryParameter("cache_local")?.toBoolean()
//            val requestBuilder = request.newBuilder()
//
//            when (isCaching) {
//                true -> requestBuilder.cacheControl(
//                    CacheControl.Builder().onlyIfCached().maxStale(10, TimeUnit.SECONDS).build()
//                )
//
//                false -> requestBuilder.cacheControl(CacheControl.FORCE_NETWORK)
//                else -> {}  // Optionally set a default behavior
//            }
//
//
//            if (isCaching != null) {
//                requestBuilder.url(request.url.newBuilder().removeAllQueryParameters("cache_local").build())
//            }
//            it.proceed(requestBuilder.build()) // Proceed with the modified request
//        }
//
//        .addNetworkInterceptor {
//            val request = it.request()
//            it.proceed(request).newBuilder()
//                .apply {
//
//                    if (request.header("Cache-Control") != null) {
//                        removeHeader(HEADER_PRAGMA)
//                        removeHeader(HEADER_CACHE_CONTROL)
//                        header(HEADER_CACHE_CONTROL, cacheControl.toString())
//                    }
//                }
//                .build()
//        }
        .addInterceptor(ChuckerInterceptor.Builder(context).build())

        .build()

    @[Provides Singleton]
    @Named("JustChucker")
    fun provideOkHttpWithChucker(@ApplicationContext context: Context): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(ChuckerInterceptor.Builder(context).build())
        .build()

    @[Provides Singleton]
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("http://195.158.16.140/mobile-bank/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @[Provides Singleton]
    @Named("NoAuthenticator")
    fun providesRetrofitNoAuth(@Named("JustChucker") okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("http://195.158.16.140/mobile-bank/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}

////Annotation processor
//class AuthInterceptor(val localStorage: LocalStorage) : Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val invocation = chain.request().tag(Invocation::class.java) ?: return chain.proceed(chain.request())
//
//        val needAuth = invocation.method().annotations.any { it.annotationClass == Auth::class }
//
//        return if (needAuth) {
//            chain.proceed(chain.request().newBuilder().addHeader("Authorization", "Bearer ${localStorage.accessToken}").build())
//        } else {
//            chain.proceed(chain.request())
//        }
//    }
//
//}
//
//@Target(AnnotationTarget.FUNCTION)
//annotation class Auth