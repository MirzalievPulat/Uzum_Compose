package uz.gita.entity.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.HomeData

interface HomeRepository {
    fun getTotalBalance(): Flow<Result<HomeData.TotalBalance>>

    fun getBasicInfo(): Flow<Result<HomeData.BasicInfo>>

    fun getFullInfo(): Flow<Result<HomeData.FullInfo>>

    fun updateInfo( updateInfo: HomeData.UpdateInfo): Flow<Result<HomeData.UpdateInfoResponse>>

    fun lastTransfers(): Flow<Result<List<HomeData.TransferInfo>>>

    fun getMoneyVisible():Flow<Result<Boolean>>
    fun setMoneyVisible(isVisible: Boolean):Flow<Result<Unit>>
}