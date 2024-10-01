package uz.gita.domain.cardUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.CardData

interface UpdateCardUC {
    operator fun invoke(updateCardParams: CardData.UpdateCardParams): Flow<Result<CardData.CardMessage>>
}