package uz.gita.entity.repository

//import uz.gita.entity.locale.LastTransfersPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.common.data.CardData
import uz.gita.common.data.RecentTransferData
import uz.gita.common.data.TransferData
import uz.gita.entity.extension.toResult
import uz.gita.entity.locale.LastTransfersPref
import uz.gita.entity.locale.LocalStorage
import uz.gita.entity.model.mapper.toRequest
import uz.gita.entity.model.mapper.toResponse
import uz.gita.entity.model.request.TransferRequest
import uz.gita.entity.remote.api.TransferApi
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
    private val localStorage: LocalStorage,
    private val lastTransfersPref: LastTransfersPref,
    private val transferApi: TransferApi
) : TransferRepository {
    override fun getCardOwner(owner: TransferData.CardOwnerPan): Flow<Result<TransferData.CardOwner>> = flow {
        val result = transferApi.getCardOwner(owner.toRequest())
            .toResult {
                it.toResponse()
            }
        emit(result)
    }.catch { emit(Result.failure(Exception(it))) }.flowOn(Dispatchers.IO)

    override fun getFee(getFee: TransferData.FeeForThis): Flow<Result<TransferData.GetFee>> = flow {
        val result = transferApi.getFee(getFee.toRequest())
            .toResult { it.toResponse() }
        emit(result)
    }.catch { emit(Result.failure(Exception(it))) }.flowOn(Dispatchers.IO)

    override fun transfer(transfer: TransferData.Transfer): Flow<Result<Unit>> = flow {
        val result = transferApi.transfer(transfer.toRequest())
            .toResult { localStorage.token = it.token }
        emit(result)
    }.catch { emit(Result.failure(Exception(it))) }.flowOn(Dispatchers.IO)

    override fun transferVerify(transferVerify: TransferData.TransferCode): Flow<Result<TransferData.TransferVerifyMessage>> =
        flow {
            val result = transferApi.transferVerify(transferVerify.toRequest(localStorage))
                .toResult {
                    localStorage.token = it.message
                    it.toResponse()
                }
            emit(result)
        }.catch { emit(Result.failure(Exception(it))) }.flowOn(Dispatchers.IO)

    override fun getHistory(): Flow<Result<TransferData.History>> = flow {
        val result = transferApi.getHistory(8, 0).toResult {
            it.toResponse()
        }
        emit(result)
    }.catch { emit(Result.failure(Exception(it))) }.flowOn(Dispatchers.IO)

    override fun transferResend(): Flow<Result<Unit>> = flow {
        val result = transferApi.transferResend(TransferRequest.TransferResend(localStorage.token)).toResult {
            localStorage.token = it.token
        }
        emit(result)
    }.catch { emit(Result.failure(Exception(it))) }.flowOn(Dispatchers.IO)



    override fun getLastTransferredCards(): Flow<Result<List<RecentTransferData>>> = flow {
        val result = lastTransfersPref.getCards()
        emit(Result.success(result))
    }.catch { emit(Result.failure(Exception(it))) }.flowOn(Dispatchers.IO)

    override fun saveLastTransferredCard(recentTransferData: RecentTransferData): Flow<Result<Unit>> = flow {
        val newList = ArrayList(lastTransfersPref.getCards())
        newList.add(recentTransferData)
        lastTransfersPref.saveCards(newList)
        emit(Result.success(Unit))
    }.catch { emit(Result.failure(Exception(it))) }.flowOn(Dispatchers.IO)



    override fun getPhoneNumber(): Flow<Result<String>> = flow {
        val result = localStorage.phone
        emit(Result.success(result))
    }.catch { emit(Result.failure(Exception(it))) }.flowOn(Dispatchers.IO)

    override fun saveTransferringCardDetails(sum: String, pan: String): Flow<Result<Unit>> = flow {
        localStorage.transferringAmount = sum
        localStorage.transferringPan = pan
        emit(Result.success(Unit))
    }.catch { emit(Result.failure(Exception(it))) }.flowOn(Dispatchers.IO)

    override fun getTransferringCardDetails(): Flow<Result<Pair<String, String>>> = flow {
        val sum = localStorage.transferringAmount
        val pan = localStorage.transferringPan
        emit(Result.success(Pair(sum, pan)))
    }.catch { emit(Result.failure(Exception(it))) }.flowOn(Dispatchers.IO)
}