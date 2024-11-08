package uz.gita.domain.transferUseCase.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.HomeData
import uz.gita.domain.transferUseCase.GetHistoryPagingUC
import uz.gita.entity.paging.MyDataSource
import javax.inject.Inject

class GetHistoryPagingUCImpl @Inject constructor(private val myDataSource: MyDataSource) : GetHistoryPagingUC {
    override fun invoke(): Flow<PagingData<HomeData.TransferInfo>> {
        val pager =
            Pager(PagingConfig(pageSize = 8, initialLoadSize = 8, enablePlaceholders = true, jumpThreshold = 8),
                1) {
                myDataSource
            }
        return pager.flow
    }
}