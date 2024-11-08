package uz.gita.entity.repository

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.common.data.CardData
import uz.gita.common.other.NetworkStatusValidator
import uz.gita.entity.extension.LocalCacheControlValue
import uz.gita.entity.extension.toResult
import uz.gita.entity.locale.LocalStorage
import uz.gita.entity.model.mapper.toRequest
import uz.gita.entity.model.mapper.toResponse
import uz.gita.entity.remote.api.CardApi
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val cardApi: CardApi,
    private val storage: LocalStorage,
    private val networkStatusValidator: NetworkStatusValidator
) : CardRepository {

    val gson = Gson()

    override fun getCard(): Flow<Result<List<CardData.CardInfo>>> = flow {


        val result1 = cardApi.getCards(LocalCacheControlValue.Cache).toResult {
            Log.d("TAG", "getCard: cardList1:$it")
            val cl = it.map { it.toResponse() }
            storage.cardList = gson.toJson(cl)
            cl
        }
        emit(result1)

        if (networkStatusValidator.isNetworkEnabled){
            val result2 = cardApi.getCards(LocalCacheControlValue.Network).toResult {
                Log.d("TAG", "getCard: cardList2:$it")
                val cl = it.map { it.toResponse() }
                storage.cardList = gson.toJson(cl)
                cl
            }
            emit(result2)
        }

    }.catch { emit(Result.failure(Exception(it))) }.flowOn(Dispatchers.IO)

    override fun addCard(newCardParams: CardData.NewCardParams): Flow<Result<CardData.CardMessage>> = flow {
        Log.d("TAG", "addCard: ishladi")
        val result = cardApi.addCard(newCardParams.toRequest()).toResult {
            Log.d("TAG", "addCard:success ${it.message}")
            it.toResponse()
        }
        emit(result)

    }.catch { Log.d("TAG", "addCard: catch ishladi e");emit(Result.failure(Exception(it.message))) }.flowOn(Dispatchers.IO)

    override fun updateCard(updateCardParams: CardData.UpdateCardParams): Flow<Result<CardData.CardMessage>> = flow {

        val result = cardApi.updateCard(updateCardParams.toRequest()).toResult {
            it.toResponse()
        }
        emit(result)

    }.catch { emit(Result.failure(Exception(it))) }.flowOn(Dispatchers.IO)

    override fun deleteCard(id: String): Flow<Result<CardData.CardMessage>> = flow {

        val result = cardApi.deleteCard(id).toResult {
            it.toResponse()
        }
        emit(result)

    }.catch { emit(Result.failure(Exception(it))) }.flowOn(Dispatchers.IO)
}