package uz.gita.domain.cardUseCase.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.CardData
import uz.gita.entity.repository.CardRepository
import uz.gita.domain.cardUseCase.GetCardsUC
import javax.inject.Inject

class GetCardsUCImpl @Inject constructor(private val repository: CardRepository):GetCardsUC {
    override fun invoke(): Flow<Result<List<CardData.CardInfo>>> {
        Log.d("TAG", "invoke: getCards")
        return repository.getCard()
    }
}