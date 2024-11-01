package uz.gita.domain.transferUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.TransferData

interface GetCardOwnerUC {
    operator fun invoke(pan:TransferData.CardOwnerPan):Flow<Result<TransferData.CardOwner>>
}