package uz.gita.presentation.home.home

import org.orbitmvi.orbit.ContainerHost
import uz.gita.common.data.CardData

interface HomePageContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val isLoading: Boolean = false,
        val totalBalance:String = "0",
        val cardList:List<CardData.CardInfo> = emptyList()
    )

    sealed interface SideEffect {
        data class ToastMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun goAddCardScreen()
    }

    interface Intent {
        object AddCardClick: Intent
        object UpdateClick: Intent
    }
}