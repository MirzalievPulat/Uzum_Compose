package uz.gita.presentation.auth.signUpVerify

import org.orbitmvi.orbit.ContainerHost
import uz.gita.common.other.NetworkStatusValidator
import kotlin.random.Random

interface SignUpVerifyContract {

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
        object SelectResend: Intent
        data class GoToPin(
            val code:String
        ): Intent
        object DismissDialog:Intent
        object SelectBack: Intent
    }
}