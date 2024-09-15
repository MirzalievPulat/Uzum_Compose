package uz.gita.data.model.request

import com.google.gson.annotations.SerializedName

sealed interface CardRequest {
    data class AddCard(
        val pan: String,
        @SerializedName("expired-year")
        val expiredYear: String,
        @SerializedName("expired-month")
        val expiredMonth: String,
        val name: String
    ) : CardRequest

    data class UpdateCard(
        val id: String,
        val name: String,
        @SerializedName("theme-type")
        val themeType: String,
        @SerializedName("is-visible")
        val isVisible: String
    ) : CardRequest


}