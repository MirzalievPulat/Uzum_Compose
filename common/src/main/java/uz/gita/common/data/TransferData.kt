package uz.gita.common.data


interface TransferData {
    data class FeeForThis(
        val senderId: String,
        val receiver: String,
        val amount: String
    )

    data class Transfer(
        val type: String,
        val senderId: String,
        val receiverPan: String,
        val amount: String
    )

    data class TransferCode(
        val code:String
    )

    data class CardOwnerPan(val pan: String)
    data class CardOwner(val name: String)

    data class GetFee(
        val fee: String,
        val amount: String
    )

    data class TransferVerifyMessage(val message: String)

    data class History(
        val transfers: List<HomeData.TransferInfo>
    )
}