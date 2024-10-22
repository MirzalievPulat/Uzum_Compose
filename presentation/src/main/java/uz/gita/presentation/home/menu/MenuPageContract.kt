package uz.gita.presentation.home.menu

import org.orbitmvi.orbit.ContainerHost
import uz.gita.common.data.CardData

interface MenuPageContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val isLoading: Boolean = false,
    )

    sealed interface SideEffect {
        data class ToastMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun goAddCardScreen()
    }

    interface Intent {
        object AddCardClick: Intent
    }
}