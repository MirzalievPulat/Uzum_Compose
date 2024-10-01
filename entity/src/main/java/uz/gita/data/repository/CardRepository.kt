package uz.gita.data.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.CardData
import uz.gita.data.model.request.CardRequest
import uz.gita.data.model.response.CardResponse

interface CardRepository {
    fun getCard():Flow<Result<List<CardData.CardInfo>>>
    fun addCard(newCardParams: CardData.NewCardParams): Flow<Result<CardData.CardMessage>>
    fun updateCard(updateCardParams: CardData.UpdateCardParams): Flow<Result<CardData.CardMessage>>
    fun deleteCard(id:String): Flow<Result<CardData.CardMessage>>

}