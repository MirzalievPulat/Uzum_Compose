package uz.gita.common.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

sealed interface CardData {
    data class CardInfo(
        val id: String ="",
        val name: String = "",
        val amount: String = "",
        val owner: String = "",
        val pan: String = "",
        val expiredYear: String = "",
        val expiredMonth: String = "",
        val themeType: String = "",
        val isVisible: String = ""
    ):Serializable

    data class CardMessage(val message:String)


    data class NewCardParams(
        val pan: String,
        @SerializedName("expired-year")
        val expiredYear: String,
        @SerializedName("expired-month")
        val expiredMonth: String,
        val name: String
    )

    data class UpdateCardParams(
        val id: String,
        val name: String,
        @SerializedName("theme-type")
        val themeType: String,
        @SerializedName("is-visible")
        val isVisible: String
    )
}