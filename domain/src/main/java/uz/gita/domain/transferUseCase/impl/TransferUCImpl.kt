package uz.gita.domain.transferUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.TransferData
import uz.gita.domain.transferUseCase.GetCardOwnerUC
import uz.gita.domain.transferUseCase.GetFeeUC
import uz.gita.domain.transferUseCase.TransferUC
import uz.gita.entity.repository.TransferRepository
import javax.inject.Inject

class TransferUCImpl @Inject constructor(private val transferRepository: TransferRepository): TransferUC {
    override fun invoke(transfer: TransferData.Transfer): Flow<Result<Unit>>  {
        return  transferRepository.transfer(transfer)
    }
}