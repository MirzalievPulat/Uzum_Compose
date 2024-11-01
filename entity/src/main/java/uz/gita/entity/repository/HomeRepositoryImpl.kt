package uz.gita.entity.repository

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.common.data.HomeData
import uz.gita.entity.extension.toResult
import uz.gita.entity.locale.LocalStorage
import uz.gita.entity.model.mapper.toRequest
import uz.gita.entity.model.mapper.toResponse
import uz.gita.entity.model.response.ErrorMessage
import uz.gita.entity.remote.api.HomeApi
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi,
    private val storage: LocalStorage
) : HomeRepository {

    private val gson = Gson()

    override fun getTotalBalance(): Flow<Result<HomeData.TotalBalance>> = flow {

        emit(Result.success(HomeData.TotalBalance(storage.lastTotalBalance)))
        val response = homeApi.getTotalBalance()

        if (response.isSuccessful && response.body() != null) {
            Log.d("TAG", "getTotalBalance: Home repository totalBalance:${response.body()}")
            emit(Result.success(response.body()!!.toResponse()))
            storage.lastTotalBalance = response.body()!!.totalBalance
        } else if (response.errorBody() != null) {
            val errorMessage = gson.fromJson(response.errorBody()!!.string(), ErrorMessage::class.java)
            emit(Result.failure(Exception(errorMessage.message)))
        } else {
            emit(Result.failure(Exception("Unknown error")))
        }
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun getBasicInfo(): Flow<Result<HomeData.BasicInfo>> = flow {
        val result = homeApi.getBasicInfo().toResult {
            storage.name = it.firsName
            it.toResponse()
        }
        emit(result)
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun getFullInfo(): Flow<Result<HomeData.FullInfo>> = flow {

        val result = homeApi.getFullInfo().toResult {
            storage.name = it.firstName
            it.toResponse()
        }
        emit(result)
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun updateInfo(updateInfo: HomeData.UpdateInfo): Flow<Result<HomeData.UpdateInfoResponse>> = flow {
        val result = homeApi.updateInfo(updateInfo.toRequest()).toResult {
            storage.name = updateInfo.firstName
            it.toResponse()
        }
        emit(result)
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun lastTransfers(): Flow<Result<List<HomeData.TransferInfo>>> = flow {
        val result = homeApi.lastTransfers().toResult {
            Log.d("TAG", "lastTransfers: $it")
            it.map { it.toResponse() }
        }
        emit(result)
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun setMoneyVisible(isVisible: Boolean): Flow<Result<Unit>> = flow{
        storage.isMoneyVisible = isVisible
        emit(Result.success(Unit))
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun getMoneyVisible(): Flow<Result<Boolean>> = flow {
        emit(Result.success(storage.isMoneyVisible))
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)
}