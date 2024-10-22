package uz.gita.presentation.auth.setPin

import org.orbitmvi.orbit.ContainerHost

interface SetPinContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val errorAnim:Long = 0,
        val isSecondTime:Boolean = false,
        val fourDigitCorrect:Boolean = true,
        val currentCode:String = ""
    )

    sealed interface SideEffect {
        data class Message(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun moveToMainScreen()
    }

    interface Intent {
        object ClickBack:Intent
        data class ClickDigit(val digit:String):Intent
        object ClickDelete:Intent
    }
}