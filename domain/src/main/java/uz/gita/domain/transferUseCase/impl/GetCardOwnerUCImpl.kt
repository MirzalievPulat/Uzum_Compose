package uz.gita.domain.transferUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.TransferData
import uz.gita.domain.transferUseCase.GetCardOwnerUC
import uz.gita.entity.repository.TransferRepository
import javax.inject.Inject

class GetCardOwnerUCImpl @Inject constructor(private val transferRepository: TransferRepository):GetCardOwnerUC {
    override fun invoke(pan: TransferData.CardOwnerPan): Flow<Result<TransferData.CardOwner>>  {
        return  transferRepository.getCardOwner(pan)
    }
}