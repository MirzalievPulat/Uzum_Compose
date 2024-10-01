package uz.gita.domain.homeUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.HomeData
import uz.gita.data.repository.HomeRepository
import uz.gita.domain.homeUseCase.GetBasicInfoUC
import javax.inject.Inject

class GetBasicInfoImpl @Inject constructor(private val repository:HomeRepository): GetBasicInfoUC{
    override fun invoke(): Flow<Result<HomeData.BasicInfo>> {
        return repository.getBasicInfo()
    }
}