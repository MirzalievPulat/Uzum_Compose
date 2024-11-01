package uz.gita.presentation.pages.home

import org.orbitmvi.orbit.ContainerHost
import uz.gita.common.data.CardData

interface HomePageContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val isLoading: Boolean = false,
        val networkDialog:Boolean = false,
        val totalBalance:String = "0",
        val isMoneyVisible:Boolean = false,
        val cardList:List<CardData.CardInfo> = emptyList()
    )

    sealed interface SideEffect {
        data class ToastMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun goUpdateCard(cardInfo: CardData.CardInfo)
        suspend fun goProfileScreen()
        suspend fun goMonitoring()
    }

    interface Intent {
        object UpdateClick: Intent
        object EyeClick:Intent
        object NetworkCloseClick:Intent
        object ProfileClick:Intent
        object MonitoringClick:Intent
        data class CardClick(val cardInfo: CardData.CardInfo):Intent
    }
}