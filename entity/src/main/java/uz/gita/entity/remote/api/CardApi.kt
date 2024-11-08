package uz.gita.entity.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import uz.gita.entity.di.Auth
import uz.gita.entity.extension.LocalCacheControl
import uz.gita.entity.extension.LocalCacheControlValue
import uz.gita.entity.model.request.CardRequest
import uz.gita.entity.model.response.CardResponse

interface CardApi {

    @GET("v1/card")
//    @Auth
    suspend fun getCards(@Header("test") @LocalCacheControl control: LocalCacheControlValue):Response<List<CardResponse.CardInfo>>

    @POST("v1/card")
    suspend fun addCard(@Body addCard: CardRequest.AddCard):Response<CardResponse.CardMessage>

    @PUT("v1/card")
    suspend fun updateCard(@Body updateCard: CardRequest.UpdateCard):Response<CardResponse.CardMessage>

    @DELETE("v1/card/{id}")
    suspend fun deleteCard(@Path("id") id:String):Response<CardResponse.CardMessage>
}