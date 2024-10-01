package uz.gita.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.common.data.CardData
import uz.gita.data.extension.toResult
import uz.gita.data.locale.LocalStorage
import uz.gita.data.model.mapper.toRequest
import uz.gita.data.model.mapper.toResponse
import uz.gita.data.model.request.CardRequest
import uz.gita.data.model.response.CardResponse
import uz.gita.data.remote.api.CardApi
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val cardApi: CardApi,
    private val storage: LocalStorage
):CardRepository {

    val gson = Gson()

    override fun getCard(): Flow<Result<List<CardData.CardInfo>>> = flow{

        val type = object :TypeToken<List<CardData.CardInfo>>(){}.type
        val cardList = gson.fromJson<List<CardData.CardInfo>>(storage.cardList,type)
        emit(Result.success(cardList))

        val result = cardApi.getCards().toResult {
            val cl = it.map { it.toResponse() }
            storage.cardList = gson.toJson(cl)
            cl
        }
        emit(result)
    }.catch { emit(Result.failure(Exception(it))) }.flowOn(Dispatchers.IO)

    override fun addCard(newCardParams: CardData.NewCardParams): Flow<Result<CardData.CardMessage>> = flow{

        val result = cardApi.addCard(newCardParams.toRequest()).toResult {
            it.toResponse()
        }
        emit(result)

    }.catch { emit(Result.failure(Exception(it))) }.flowOn(Dispatchers.IO)

    override fun updateCard(updateCardParams: CardData.UpdateCardParams): Flow<Result<CardData.CardMessage>> = flow{

        val result = cardApi.updateCard(updateCardParams.toRequest()).toResult {
            it.toResponse()
        }
        emit(result)

    }.catch { emit(Result.failure(Exception(it))) }.flowOn(Dispatchers.IO)

    override fun deleteCard(id:String): Flow<Result<CardData.CardMessage>> = flow{

        val result = cardApi.deleteCard(id).toResult {
            it.toResponse()
        }
        emit(result)

    }.catch { emit(Result.failure(Exception(it))) }.flowOn(Dispatchers.IO)
}