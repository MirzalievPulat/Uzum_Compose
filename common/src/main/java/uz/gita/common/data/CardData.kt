package uz.gita.common.data

import com.google.gson.annotations.SerializedName

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
    ):CardData
}