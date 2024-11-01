package uz.gita.domain.transferUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.TransferData

interface TransferUC {
    operator fun invoke(transfer:TransferData.Transfer):Flow<Result<Unit>>
}
