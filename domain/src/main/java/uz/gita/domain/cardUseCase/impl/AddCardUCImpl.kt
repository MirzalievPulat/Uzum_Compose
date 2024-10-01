package uz.gita.domain.cardUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.CardData
import uz.gita.data.repository.CardRepository
import uz.gita.domain.cardUseCase.AddCardUC
import javax.inject.Inject

class AddCardUCImpl @Inject constructor(private val repository: CardRepository):AddCardUC {
    override fun invoke(newCardParams: CardData.NewCardParams): Flow<Result<CardData.CardMessage>> {
        return repository.addCard(newCardParams)
    }
}