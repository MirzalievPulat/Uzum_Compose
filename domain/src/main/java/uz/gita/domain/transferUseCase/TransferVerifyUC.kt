package uz.gita.domain.transferUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.TransferData

interface TransferVerifyUC {
    operator fun invoke(transferCode: TransferData.TransferCode):Flow<Result<TransferData.TransferVerifyMessage>>
}
