package uz.gita.domain.transferUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.CardData
import uz.gita.common.data.RecentTransferData
import uz.gita.common.data.TransferData
import uz.gita.domain.transferUseCase.GetCardOwnerUC
import uz.gita.domain.transferUseCase.GetFeeUC
import uz.gita.domain.transferUseCase.GetLastTransferredCardsUC
import uz.gita.entity.repository.TransferRepository
import javax.inject.Inject

class GetLastTransferredCardsUCImpl @Inject constructor(private val transferRepository: TransferRepository):GetLastTransferredCardsUC {
    override fun invoke(): Flow<Result<List<RecentTransferData>>> {
        return transferRepository.getLastTransferredCards()
    }

}