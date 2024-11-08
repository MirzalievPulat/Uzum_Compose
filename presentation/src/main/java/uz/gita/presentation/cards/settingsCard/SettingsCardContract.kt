package uz.gita.presentation.cards.settingsCard

import androidx.compose.runtime.MutableState
import org.orbitmvi.orbit.ContainerHost
import uz.gita.common.data.CardData
import uz.gita.common.data.HomeData
import uz.gita.common.other.CardsType

interface SettingsCardContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val cardLoading:Boolean = false,
        val networkDialog:Boolean = false,
        val saveDialog:Boolean = false,
        val saveLoading:Boolean = false,
        val myCards:List<CardData.CardInfo> = listOf(
            CardData.CardInfo("","","100000","Polat","9898"),
            CardData.CardInfo("","","100000","Polat","9898"),
            CardData.CardInfo("","","100000","Polat","9898"),
        ),
    )

    sealed interface SideEffect {
        data class ToastMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun goBack()
        suspend fun goAddCard()
    }

    interface Intent {
        data class ToggleClick(
            val isVisible:Boolean,
            val index: Int
        ):Intent
        object AddCard:Intent
        object YesClick:Intent
        object Back: Intent
        data class NetworkDialogDismiss(val quit:Boolean): Intent

    }
}