package uz.gita.uzumcompose.presentation.auth.signIn

import org.orbitmvi.orbit.ContainerHost

interface SignInContract {

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
        suspend fun moveToSignUp()
        suspend fun moveToVerify(phoneNumber:String)
    }

    interface Intent {
        data class ClickContinue(
            val phone: String,
            val password: String,
        ) : Intent

        object SelectSignUp:Intent
    }
}