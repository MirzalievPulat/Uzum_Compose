package uz.gita.data.remote.api

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import uz.gita.data.model.request.HomeRequest
import uz.gita.data.model.response.HomeResponse

interface HomeApi {

    @GET("v1/home/total-balance")
    suspend fun getTotalBalance(): Response<HomeResponse.TotalBalance>

    @GET("v1/home/user-info")
    suspend fun getBasicInfo():Response<HomeResponse.BasicInfo>

    @GET("v1/home/user-info/details")
    suspend fun getFullInfo():Response<HomeResponse.FullInfo>

    @PUT("v1/home/user-info")
    suspend fun updateInfo(@Body updateInfo: HomeRequest.UpdateInfo):Response<HomeResponse.FullInfo>

    @GET("v1/home/user-info")
    suspend fun lastTransfers():Response<List<HomeResponse.TransferInfo>>
}