package uz.gita.domain.cardUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.CardData

interface DeleteCardUC {
    operator fun invoke(id:String): Flow<Result<CardData.CardMessage>>
}