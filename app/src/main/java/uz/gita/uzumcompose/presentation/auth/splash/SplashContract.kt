package uz.gita.uzumcompose.presentation.auth.splash

sealed interface SplashContract {


    interface Direction {
        suspend fun moveToSignUp()
        suspend fun moveToSignIn()
        suspend fun moveToPin()
    }


}