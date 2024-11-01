package uz.gita.domain.transferUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.CardData
import uz.gita.common.data.RecentTransferData
import uz.gita.common.data.TransferData
import uz.gita.domain.transferUseCase.GetCardOwnerUC
import uz.gita.domain.transferUseCase.GetFeeUC
import uz.gita.domain.transferUseCase.SaveLastTransferredCardsUC
import uz.gita.entity.repository.TransferRepository
import javax.inject.Inject

class SaveLastTransferredCardsUCImpl @Inject constructor(private val transferRepository: TransferRepository):SaveLastTransferredCardsUC {
    override fun invoke(recentTransferData: RecentTransferData): Flow<Result<Unit>> {
        return transferRepository.saveLastTransferredCard(recentTransferData)
    }
}