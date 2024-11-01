package uz.gita.entity.repository

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import uz.gita.common.data.CardData
import uz.gita.common.data.RecentTransferData
import uz.gita.common.data.TransferData

interface TransferRepository {
    fun getCardOwner(owner: TransferData.CardOwnerPan): Flow<Result<TransferData.CardOwner>>

    fun getFee(getFee: TransferData.FeeForThis): Flow<Result<TransferData.GetFee>>

    fun transfer(transfer: TransferData.Transfer): Flow<Result<Unit>>

    fun transferVerify(transferVerify: TransferData.TransferCode): Flow<Result<TransferData.TransferVerifyMessage>>

    fun getHistory(): Flow<Result<TransferData.History>>

    fun transferResend(): Flow<Result<Unit>>



    fun getLastTransferredCards():Flow<Result<List<RecentTransferData>>>

    fun saveLastTransferredCard(recentTransferData: RecentTransferData):Flow<Result<Unit>>



    fun getPhoneNumber():Flow<Result<String>>

    fun saveTransferringCardDetails(sum:String,pan:String):Flow<Result<Unit>>

    fun getTransferringCardDetails():Flow<Result<Pair<String,String>>>
}