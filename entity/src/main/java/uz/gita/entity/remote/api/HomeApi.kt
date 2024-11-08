package uz.gita.entity.remote.api

import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Query
import uz.gita.entity.di.Auth
import uz.gita.entity.extension.LocalCacheControl
import uz.gita.entity.extension.LocalCacheControlValue
import uz.gita.entity.model.request.HomeRequest
import uz.gita.entity.model.response.HomeResponse

interface HomeApi {

    @GET("v1/home/total-balance")
    @Auth
    suspend fun getTotalBalance(): Response<HomeResponse.TotalBalance>

    @GET("v1/home/user-info")
    suspend fun getBasicInfo():Response<HomeResponse.BasicInfo>

    @GET("v1/home/user-info/details")
    suspend fun getFullInfo():Response<HomeResponse.FullInfo>

    @PUT("v1/home/user-info")
    suspend fun updateInfo(@Body updateInfo: HomeRequest.UpdateInfo):Response<HomeResponse.UpdateInfo>

//    @GET("v1/home/last-transfers")
//    suspend fun lastTransfers(@Query("cache_local") cache_local:Boolean):Response<List<HomeResponse.TransferInfo>>

    @GET("v1/home/last-transfers")
    @Auth
    suspend fun lastTransfers(@Header("test") @LocalCacheControl control: LocalCacheControlValue):Response<List<HomeResponse
        .TransferInfo>>
}