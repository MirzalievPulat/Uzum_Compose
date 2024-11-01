package uz.gita.domain.transferUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.TransferData
import uz.gita.domain.transferUseCase.GetCardOwnerUC
import uz.gita.domain.transferUseCase.GetFeeUC
import uz.gita.domain.transferUseCase.GetHistoryUC
import uz.gita.domain.transferUseCase.TransferUC
import uz.gita.entity.repository.TransferRepository
import javax.inject.Inject

class GetHistoryUCImpl @Inject constructor(private val transferRepository: TransferRepository): GetHistoryUC {
    override fun invoke(): Flow<Result<TransferData.History>>  {
        return  transferRepository.getHistory()
    }
}