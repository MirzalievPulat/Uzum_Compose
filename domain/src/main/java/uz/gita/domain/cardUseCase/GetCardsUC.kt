package uz.gita.domain.cardUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.CardData

interface GetCardsUC {
    operator fun invoke(): Flow<Result<List<CardData.CardInfo>>>
}