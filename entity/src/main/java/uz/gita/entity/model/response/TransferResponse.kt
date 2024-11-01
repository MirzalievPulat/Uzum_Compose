package uz.gita.entity.model.response

import com.google.gson.annotations.SerializedName

sealed interface TransferResponse {
    data class GetCardOwner(val pan: String)

    data class GetFee(
        val fee: String,
        val amount: String
    )

    data class Transfer(val token: String)

    data class TransferVerify(val message: String)

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
    )
}