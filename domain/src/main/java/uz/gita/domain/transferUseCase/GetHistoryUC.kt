package uz.gita.domain.transferUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.TransferData

interface GetHistoryUC {
    operator fun invoke():Flow<Result<TransferData.History>>
}
