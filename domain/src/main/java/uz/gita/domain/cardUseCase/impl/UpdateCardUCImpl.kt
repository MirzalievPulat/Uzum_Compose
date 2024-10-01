package uz.gita.domain.cardUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.CardData
import uz.gita.data.repository.CardRepository
import uz.gita.domain.cardUseCase.UpdateCardUC
import javax.inject.Inject

class UpdateCardUCImpl @Inject constructor(private val repository: CardRepository):UpdateCardUC {
    override fun invoke(updateCardParams: CardData.UpdateCardParams): Flow<Result<CardData.CardMessage>> {
        return repository.updateCard(updateCardParams)
    }
}