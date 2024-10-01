package uz.gita.domain.homeUseCase.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.HomeData
import uz.gita.data.repository.HomeRepository
import uz.gita.domain.homeUseCase.GetTotalBalanceUC
import javax.inject.Inject

class GetTotalBalanceImpl @Inject constructor(private val repository: HomeRepository):GetTotalBalanceUC {
    override fun invoke(): Flow<Result<HomeData.TotalBalance>> {
        Log.d("TAG", "invoke: totalbalance")
        return repository.getTotalBalance()
    }
}