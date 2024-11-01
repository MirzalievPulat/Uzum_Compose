package uz.gita.presentation.pages.transfer

import org.orbitmvi.orbit.ContainerHost

interface TransferPageContract {

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
        suspend fun goTransferCardScreen()
    }

    interface Intent {
        object PanTextFieldClick: Intent
    }
}