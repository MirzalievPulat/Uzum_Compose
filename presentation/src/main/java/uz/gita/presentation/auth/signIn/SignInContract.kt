package uz.gita.presentation.auth.signIn

import org.orbitmvi.orbit.ContainerHost
import uz.gita.common.other.NetworkStatusValidator

interface SignInContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val isLoading:Boolean = false,
        val networkDialog:Boolean = false,

        val phoneNumberError: String? = null,
        val passwordError: String? = null,

        val networkStatusValidator: NetworkStatusValidator? = null
    )


    sealed interface SideEffect {
        data class Message(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun moveToSignUp()
        suspend fun moveToVerify(phoneNumber:String)
    }

    interface Intent {
        data class ClickContinue(
            val phone: String,
            val password: String,
        ) : Intent

        object SelectSignUp: Intent
        object DismissDialog: Intent
    }
}