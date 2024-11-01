package uz.gita.entity.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import uz.gita.entity.model.request.TransferRequest
import uz.gita.entity.model.response.TransferResponse

interface TransferApi {

    @POST("v1/transfer/card-owner")
    suspend fun getCardOwner(@Body cardOwner: TransferRequest.GetCardOwner): Response<TransferResponse.GetCardOwner>

    @POST("v1/transfer/fee")
    suspend fun getFee(@Body getFee: TransferRequest.GetFee): Response<TransferResponse.GetFee>

    @POST("v1/transfer/transfer")
    suspend fun transfer(@Body transfer: TransferRequest.Transfer): Response<TransferResponse.Transfer>

    @POST("v1/transfer/transfer/verify")
    suspend fun transferVerify(@Body transferVerify: TransferRequest.TransferVerify): Response<TransferResponse.TransferVerify>

    @GET("v1/transfer/history")
    suspend fun getHistory(
        @Query("size") size: Int,
        @Query("current-page") currentPage: Int
    ): Response<TransferResponse.GetHistory>

    @POST("v1/transfer/transfer/resend")
    suspend fun transferResend(@Body transferResend: TransferRequest.TransferResend): Response<TransferResponse.TransferResend>
}