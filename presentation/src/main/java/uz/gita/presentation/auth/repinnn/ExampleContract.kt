package uz.gita.presentation.auth.repinnn

import org.orbitmvi.orbit.ContainerHost

interface ExampleContract {

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
        suspend fun moveBack()
    }

    interface Intent {
        object ClickBack:Intent
        data class ClickDigit(val digit:String):Intent
        object ClickDelete:Intent
    }
}