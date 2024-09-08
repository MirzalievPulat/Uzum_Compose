package uz.gita.uzumcompose.presentation.signUp

import org.orbitmvi.orbit.ContainerHost

interface SignUpContract {

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
        suspend fun moveToSignIn()
        suspend fun moveToVerify()
    }

    interface Intent {
        data class ClickContinue(
            val firstName: String,
            val lastName: String,
            val password: String,
            val phone: String,
            val birthDate:String,
            val genderType: Int
        ) : Intent
        object SelectSignIn:Intent
    }
}