package uz.gita.domain.transferUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.TransferData

interface GetPhoneNumberUC {
    operator fun invoke():Flow<Result<String>>
}
