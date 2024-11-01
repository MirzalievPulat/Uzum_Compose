package uz.gita.domain.transferUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.CardData

interface GetTransferringCardDetails {
    operator fun invoke(): Flow<Result<Pair<String,String>>>
}