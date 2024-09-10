package uz.gita.uzumcompose.presentation.pin

import org.orbitmvi.orbit.ContainerHost

interface PinContract {

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
        suspend fun moveToRePinScreen(code:String)
    }

    interface Intent {
        data class GoNextScreen(
            val code1:String,
        ):Intent
    }
}