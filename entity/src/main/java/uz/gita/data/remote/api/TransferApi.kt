package uz.gita.data.remote.api

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import uz.gita.data.model.request.TransferRequest
import uz.gita.data.model.response.TransferResponse

interface TransferApi {

    @POST("v1/transfer/card-owner")
    suspend fun getCardOwner():Response<TransferResponse.GetCardOwner>

    @POST("v1/transfer/fee")
    suspend fun getFee(@Body getFee: TransferRequest.GetFee):Response<TransferResponse.GetFee>

    @POST("v1/transfer/transfer")
    suspend fun transfer(@Body transfer: TransferRequest.Transfer):Response<TransferResponse.Transfer>

    @POST("v1/transfer/transfer/verify")
    suspend fun transferVerify(@Body transferVerify: TransferRequest.TransferVerify):Response<TransferResponse.TransferVerify>

    @GET("v1/transfer/history")
    suspend fun getHistory():Response<TransferResponse.GetHistory>

    @POST("v1/transfer/transfer/resend")
    suspend fun transferResend(@Body transferResend: TransferRequest.TransferResend):Response<TransferResponse.TransferResend>
}