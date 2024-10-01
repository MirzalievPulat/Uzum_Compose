package uz.gita.data.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.HomeData
import uz.gita.data.model.request.HomeRequest
import uz.gita.data.model.response.HomeResponse

interface HomeRepository {
    fun getTotalBalance(): Flow<Result<HomeData.TotalBalance>>

    fun getBasicInfo(): Flow<Result<HomeData.BasicInfo>>

    fun getFullInfo(): Flow<Result<HomeData.FullInfo>>

    fun updateInfo( updateInfo: HomeData.UpdateInfo): Flow<Result<HomeData.UpdateInfoResponse>>

    fun lastTransfers(): Flow<Result<List<HomeData.TransferInfo>>>
}