package uz.gita.domain.transferUseCase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.HomeData
import uz.gita.common.data.TransferData
import uz.gita.entity.paging.MyDataSource
import uz.gita.entity.remote.api.TransferApi

interface GetHistoryPagingUC {
    operator fun invoke():Flow<PagingData<HomeData.TransferInfo>>
}
