package uz.gita.presentation.auth.signInVerify

import org.orbitmvi.orbit.ContainerHost
import uz.gita.common.other.NetworkStatusValidator
import kotlin.random.Random

interface SignInVerifyContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val isLoading:Boolean = false,
        val networkError:Boolean = false,
        val resendCode:Float = Random.nextFloat(),
        val showProgress:Boolean = false,
        val codeError:String? = null,

        val networkStatusValidator: NetworkStatusValidator? = null
    )

    sealed interface SideEffect {
        data class Message(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun moveToPinScreen()
        suspend fun moveBack()
    }

    interface Intent {
        object SelectResend : Intent
        object SelectBack : Intent
        object DismissDialog:Intent
        data class GoToPin(
            val code: String
        ) : Intent
    }
}