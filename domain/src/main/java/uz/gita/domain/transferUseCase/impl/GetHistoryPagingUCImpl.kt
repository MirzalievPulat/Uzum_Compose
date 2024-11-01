package uz.gita.domain.transferUseCase.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.gita.common.data.HomeData
import uz.gita.common.data.TransferData
import uz.gita.domain.transferUseCase.GetHistoryPagingUC
import uz.gita.entity.paging.MyDataSource
import uz.gita.entity.repository.TransferRepository
import javax.inject.Inject

class GetHistoryPagingUCImpl @Inject constructor(private val myDataSource: MyDataSource): GetHistoryPagingUC {
    override fun invoke(): Flow<PagingData<HomeData.TransferInfo>> {
        val pager = Pager(PagingConfig(pageSize = 8, initialLoadSize = 8, enablePlaceholders = true,),0,){
            myDataSource
        }
        return pager.flow
    }
}