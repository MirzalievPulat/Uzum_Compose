package uz.gita.domain.transferUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.CardData
import uz.gita.common.data.RecentTransferData
import uz.gita.common.data.TransferData

interface GetLastTransferredCardsUC {
    operator fun invoke():Flow<Result<List<RecentTransferData>>>
}
