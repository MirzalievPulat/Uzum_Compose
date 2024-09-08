package uz.gita.uzumcompose.presentation.signIn

import org.orbitmvi.orbit.ContainerHost

interface SignInContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val isLoading:Boolean = false,
    )
//    sealed interface UIState {
//        object Loading : UIState
//        object NoInternet : UIState
//        data class DataState(
//            val isEnable: Boolean = false,
//            var isMale: Boolean = false,
//            val isFeMale: Boolean = false
//        ) : UIState
//    }

    sealed interface SideEffect {
        data class ResultMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun moveToSignUp()
        suspend fun moveToVerify()
    }

    interface Intent {
        data class ClickContinue(
            val phone: String,
            val password: String,
        ) : Intent

        object SelectSignUp:Intent
    }
}