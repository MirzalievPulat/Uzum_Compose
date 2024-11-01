package uz.gita.domain.transferUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.TransferData
import uz.gita.domain.transferUseCase.GetCardOwnerUC
import uz.gita.domain.transferUseCase.GetFeeUC
import uz.gita.domain.transferUseCase.TransferUC
import uz.gita.domain.transferUseCase.TransferVerifyUC
import uz.gita.entity.repository.TransferRepository
import javax.inject.Inject

class TransferVerifyUCImpl @Inject constructor(private val transferRepository: TransferRepository): TransferVerifyUC {
    override fun invoke(transferCode: TransferData.TransferCode): Flow<Result<TransferData.TransferVerifyMessage>>  {
        return  transferRepository.transferVerify(transferCode)
    }
}