package uz.gita.uzumcompose.presentation.repin

import org.orbitmvi.orbit.ContainerHost

interface RePinContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val isLoading:Boolean = false,
    )

    sealed interface SideEffect {
        data class ResultMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun moveToMainScreen()
        suspend fun moveBack()
    }

    interface Intent {
        object SelectBack:Intent
    }
}