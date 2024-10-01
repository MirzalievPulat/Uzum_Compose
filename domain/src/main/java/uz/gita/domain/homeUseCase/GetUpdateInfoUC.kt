package uz.gita.domain.homeUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.HomeData

interface GetUpdateInfoUC {
    operator fun invoke(updateInfo: HomeData.UpdateInfo): Flow<Result<HomeData.UpdateInfoResponse>>
}