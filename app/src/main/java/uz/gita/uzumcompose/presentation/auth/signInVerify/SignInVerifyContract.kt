package uz.gita.uzumcompose.presentation.auth.signInVerify

import org.orbitmvi.orbit.ContainerHost
import kotlin.random.Random

interface SignInVerifyContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val isLoading: Boolean = false,
        val resendCode: Float = Random.nextFloat(),
        val showProgress: Boolean = false
    )

    sealed interface SideEffect {
        data class ResultMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun moveToPinScreen()
        suspend fun moveBack()
    }

    interface Intent {
        object SelectResend : Intent
        object SelectBack : Intent
        data class GoToPin(
            val code: String
        ) : Intent
    }
}