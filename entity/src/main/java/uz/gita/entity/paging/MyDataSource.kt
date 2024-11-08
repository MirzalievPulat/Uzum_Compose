package uz.gita.entity.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import uz.gita.common.data.HomeData
import uz.gita.entity.model.mapper.toResponse
import uz.gita.entity.model.response.HomeResponse
import uz.gita.entity.model.response.TransferResponse
import uz.gita.entity.remote.api.TransferApi
import javax.inject.Inject

class MyDataSource @Inject constructor (
    private val transferApi: TransferApi,
) : PagingSource<Int, HomeData.TransferInfo>() {
    override fun getRefreshKey (state: PagingState<Int, HomeData.TransferInfo>): Int {
        return state.anchorPosition ?.inc() ?: 1
    }

    override val jumpingSupported: Boolean
        get() = true

    override suspend fun load(params: LoadParams <Int>): LoadResult <Int, HomeData.TransferInfo> {
        try {
            delay(500)
            val response = transferApi.getHistory( params.loadSize,params.key ?: 1)
            if (response.isSuccessful) {
                val ls = response.body()!!.child
                return LoadResult.Page(
                    data = ls.map { it.toResponse() },
                    prevKey = if (params.key == 1 || params.key == null) null else params.key!! - 1,
                    nextKey = if (response.body()!!.totalPages <= response.body()!!.currentPage) null else params.key!! + 1
                )
            }
            return LoadResult .Error(Exception( response .errorBody()?.string()))
        } catch (e: Throwable ) {
            e.printStackTrace()
            return LoadResult .Error(Exception( e))
        }
    }
}