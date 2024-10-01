package uz.gita.domain.cardUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.CardData

interface AddCardUC {
    operator fun invoke(newCardParams: CardData.NewCardParams): Flow<Result<CardData.CardMessage>>
}