package uz.gita.data.model.response

import com.google.gson.annotations.SerializedName

sealed interface HomeResponse {
    data class TotalBalance(
        @SerializedName("total-balance")
        val totalBalance: String
    ) : HomeResponse

    data class BasicInfo(
        @SerializedName("firsrt-name")
        val firsName: String,
        @SerializedName("gender-type")
        val genderType: String,
        @SerializedName("age")
        val age: String
    ) : HomeResponse

    data class FullInfo(
        @SerializedName("first-name")
        val firstName: String,
        @SerializedName("last-name")
        val lastName: String,
        val phone: String,
        @SerializedName("born-date")
        val birthDate: String,
        val gender: String
    ) : HomeResponse

    data class UpdateInfo(val message: String) : HomeResponse

    data class TransferInfo(
        @SerializedName("type")
        val type: String,
        @SerializedName("from")
        val from: String,
        @SerializedName("to")
        val to: String,
        @SerializedName("amount")
        val amount: String,
        @SerializedName("time")
        val time: String
    )
}
