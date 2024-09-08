package uz.gita.uzumcompose.presentation.splash

import org.orbitmvi.orbit.ContainerHost

sealed interface SplashContract {
//
//    interface ViewModel : ContainerHost<UIState, SideEffect> {}
//
//    interface UIState
//
//    sealed interface SideEffect {
//        data class Toast(val message: String): SideEffect
//    }

    interface Direction {
        suspend fun moveToSignUp()
        suspend fun moveToSignIn()
        suspend fun moveToPin()
    }


}