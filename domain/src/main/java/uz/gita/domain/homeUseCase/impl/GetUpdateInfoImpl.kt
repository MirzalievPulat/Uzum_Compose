package uz.gita.domain.homeUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.HomeData
import uz.gita.data.repository.HomeRepository
import uz.gita.domain.homeUseCase.GetTotalBalanceUC
import uz.gita.domain.homeUseCase.GetUpdateInfoUC
import javax.inject.Inject

class GetUpdateInfoImpl @Inject constructor(private val repository: HomeRepository):GetUpdateInfoUC {
    override fun invoke(updateInfo: HomeData.UpdateInfo): Flow<Result<HomeData.UpdateInfoResponse>> {
        return repository.updateInfo(updateInfo)
    }
}