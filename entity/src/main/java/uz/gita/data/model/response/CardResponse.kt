package uz.gita.data.model.response

import com.google.gson.annotations.SerializedName

sealed interface CardResponse {
    data class CardInfo(
        val id: String,
        val name: String,
        val amount: String,
        val owner: String,
        val pan: String,
        @SerializedName("expired-year")
        val expiredYear: String,
        @SerializedName("expired-month")
        val expiredMonth: String,
        @SerializedName("theme-type")
        val themeType: String,
        @SerializedName("is-visible")
        val isVisible: String
    ):CardResponse

    data class AddCard(val message:String):CardResponse
    data class UpdateCard(val message:String):CardResponse
    data class DeleteCard(val message:String):CardResponse
}