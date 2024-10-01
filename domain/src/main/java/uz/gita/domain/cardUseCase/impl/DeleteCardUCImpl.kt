package uz.gita.domain.cardUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.CardData
import uz.gita.data.repository.CardRepository
import uz.gita.domain.cardUseCase.DeleteCardUC
import uz.gita.domain.cardUseCase.UpdateCardUC
import javax.inject.Inject

class DeleteCardUCImpl @Inject constructor(private val repository: CardRepository): DeleteCardUC {
    override fun invoke(id:String): Flow<Result<CardData.CardMessage>> {
        return repository.deleteCard(id)
    }
}