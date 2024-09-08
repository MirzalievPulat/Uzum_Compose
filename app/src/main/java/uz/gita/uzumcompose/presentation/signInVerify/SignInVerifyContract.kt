package uz.gita.uzumcompose.presentation.signInVerify

import org.orbitmvi.orbit.ContainerHost

interface SignInVerifyContract {

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
        suspend fun moveToPinScreen()
        suspend fun moveBack()
    }

    interface Intent {
        object SelectResend:Intent
        object SelectNotComing:Intent
        object SelectBack:Intent
    }
}