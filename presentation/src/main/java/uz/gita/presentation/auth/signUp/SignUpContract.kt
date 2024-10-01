package uz.gita.presentation.auth.signUp

import org.orbitmvi.orbit.ContainerHost
import uz.gita.common.data.CardData
import uz.gita.common.other.GenderType

interface SignUpContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val isLoading: Boolean = false,
        val networkDialog:Boolean = false,

        val firstNameError: String? = null,
        val lastNameError: String? = null,
        val phoneNumberError: String? = null,
        val passwordError: String? = null,
        val birthDateError: String? = null,
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
        data class Message(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun moveToSignIn()
        suspend fun moveToVerify(phoneNumber: String)
    }

    interface Intent {
        data class ClickContinue(
            val firstName: String,
            val lastName: String,
            val password: String,
            val phone: String,
            val birthDate: String,
            val genderType: GenderType
        ) : Intent

        object SelectSignIn : Intent
    }
}