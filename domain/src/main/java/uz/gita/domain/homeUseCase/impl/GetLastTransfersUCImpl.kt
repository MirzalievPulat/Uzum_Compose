package uz.gita.domain.homeUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.HomeData
import uz.gita.data.repository.HomeRepository
import uz.gita.domain.homeUseCase.GetLastTransfersUC
import uz.gita.domain.homeUseCase.GetTotalBalanceUC
import javax.inject.Inject

class GetLastTransfersUCImpl @Inject constructor(private val repository: HomeRepository):GetLastTransfersUC {
    override fun invoke(): Flow<Result<List<HomeData.TransferInfo>>> {
        return repository.lastTransfers()
    }
}