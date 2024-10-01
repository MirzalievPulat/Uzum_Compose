package uz.gita.domain.homeUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.HomeData

interface GetLastTransfersUC {
    operator fun invoke(): Flow<Result<List<HomeData.TransferInfo>>>
}