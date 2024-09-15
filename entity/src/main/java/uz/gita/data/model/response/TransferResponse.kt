package uz.gita.data.model.response

import com.google.gson.annotations.SerializedName
import uz.gita.data.model.request.TransferRequest

sealed interface TransferResponse {
    data class GetCardOwner(val pan: String) : TransferResponse

    data class GetFee(
        val fee: String,
        val amount: String
    ) : TransferResponse

    data class Transfer(val token: String) : TransferResponse

    data class TransferVerify(val message: String) : TransferResponse

    data class GetHistory(
        @SerializedName("total-elements")
        val totalElements: String,
        @SerializedName("total-pages")
        val totalPages: String,
        @SerializedName("current-page")
        val currentPage: String,
        val child: List<HomeResponse.TransferInfo>
    ):TransferResponse

    data class TransferResend(
        val token: String
    ): TransferResponse
}