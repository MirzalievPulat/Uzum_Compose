package uz.gita.domain.homeUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.HomeData
import uz.gita.entity.repository.HomeRepository
import uz.gita.domain.homeUseCase.UpdateInfoUC
import javax.inject.Inject

class UpdateInfoImpl @Inject constructor(private val repository: HomeRepository):UpdateInfoUC {
    override fun invoke(updateInfo: HomeData.UpdateInfo): Flow<Result<HomeData.UpdateInfoResponse>> {
        return repository.updateInfo(updateInfo)
    }
}