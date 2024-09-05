package uz.gita.uzumcompose.presentation.splash

import org.orbitmvi.orbit.ContainerHost

sealed interface SplashContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    sealed interface UIState {
        object InitState: UIState
    }

    sealed interface SideEffect {
        data class Toast(val message: String): SideEffect
    }

    interface Direction {
        suspend fun moveToSignUp()
        suspend fun moveToSignIn()
        suspend fun moveToPin()
    }


    sealed interface Intent{
//        data object SignUp:Intent
//        data object SignIn:Intent
//        data object Pin:Intent
    }
}