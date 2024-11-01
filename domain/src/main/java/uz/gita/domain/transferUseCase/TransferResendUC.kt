package uz.gita.domain.transferUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.TransferData

interface TransferResendUC {
    operator fun invoke():Flow<Result<Unit>>
}
