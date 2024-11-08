package uz.gita.entity.extension

import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import retrofit2.Invocation
import java.util.concurrent.TimeUnit


/**
 * Created by Sherzodbek Muhammadiev on 03/11/24.
 */


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ForceLocalCache

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ForceNetwork

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalCacheControl

enum class LocalCacheControlValue {
    Cache, Network
}

@Override


fun OkHttpClient.Builder.addLocalCacheInterceptor(cache: Cache, maxAge: Int, maxStale: Int, timeUnitForStale: TimeUnit):
        OkHttpClient.Builder {
    cache(cache)
    addInterceptor {
        val request = it.request()
        val requestBuilder = request.newBuilder()

        val invocation: Invocation? = request.tag(Invocation::class.java)
        if (invocation != null) {
            val method = invocation.method()
            val values = invocation.arguments()
            method.parameterAnnotations.forEachIndexed { index, annotations ->
                annotations.forEach { annotation ->
                    when (annotation) {
                        is ForceNetwork -> requestBuilder.cacheControl(CacheControl.FORCE_NETWORK)
                        is ForceLocalCache -> requestBuilder.cacheControl(CacheControl.Builder().onlyIfCached().maxStale
                            (maxStale, timeUnitForStale).build())
                        is LocalCacheControl -> {
                            val value = values[index] as? LocalCacheControlValue
                                ?: throw IllegalArgumentException("For LocalCacheControl annotation required params must be of type LocalCacheControlValue")
                            when (value) {
                                LocalCacheControlValue.Cache -> requestBuilder.cacheControl(CacheControl.Builder().onlyIfCached
                                    ().maxStale(maxStale, timeUnitForStale).build())
                                LocalCacheControlValue.Network -> requestBuilder.cacheControl(CacheControl.FORCE_NETWORK)
                            }
                        }
                    }
                }
            }
        }
        it.proceed(requestBuilder.build())
    }
    addNetworkInterceptor {
        val request = it.request()
        it.proceed(request).newBuilder()
            .apply {
                if (request.header("Cache-Control") != null) {
                    removeHeader("pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=10000")
                }
            }
            .build()
    }
    return this
}