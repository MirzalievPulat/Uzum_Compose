package uz.gita.domain.homeUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.HomeData
import uz.gita.entity.repository.HomeRepository
import uz.gita.domain.homeUseCase.GetLastTransfersUC
import javax.inject.Inject

class GetLastTransfersUCImpl @Inject constructor(private val repository: HomeRepository):GetLastTransfersUC {
    override fun invoke(): Flow<Result<List<HomeData.TransferInfo>>> {
        return repository.lastTransfers()
    }
}