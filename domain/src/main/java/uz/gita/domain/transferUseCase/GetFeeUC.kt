package uz.gita.domain.transferUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.TransferData

interface GetFeeUC {
    operator fun invoke(feeForThis:TransferData.FeeForThis):Flow<Result<TransferData.GetFee>>
}
