package uz.gita.domain.homeUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.HomeData
import uz.gita.entity.repository.HomeRepository
import uz.gita.domain.homeUseCase.GetFullInfoUC
import javax.inject.Inject

class GetFullInfoImpl @Inject constructor(private val repository: HomeRepository):GetFullInfoUC {
    override fun invoke(): Flow<Result<HomeData.FullInfo>> {
        return repository.getFullInfo()
    }
}