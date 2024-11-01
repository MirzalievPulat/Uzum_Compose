package uz.gita.entity.model.request

import com.google.gson.annotations.SerializedName

sealed interface TransferRequest {

    data class GetCardOwner(
        val pan :String
    )
    data class GetFee(
        @SerializedName("sender-id")
        val senderId: String,
        val receiver: String,
        val amount: Int
    )

    data class Transfer(
        val type: String,
        @SerializedName("sender-id")
        val senderId: String,
        @SerializedName("receiver-pan")
        val receiverPan: String,
        val amount: String
    )

    data class TransferVerify(
        val token:String,
        val code:String
    )

    data class TransferResend(
        val token: String
    )
}