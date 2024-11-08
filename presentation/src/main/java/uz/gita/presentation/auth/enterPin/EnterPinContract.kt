package uz.gita.presentation.auth.enterPin

import org.orbitmvi.orbit.ContainerHost
import uz.gita.presentation.auth.setPin.SetPinContract.Intent

interface EnterPinContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val errorAnim:Long = 0,
        val fourDigitCorrect:Boolean = true,
        val name:String = "",
        val currentCode:String = ""
    )

    sealed interface SideEffect {
        data class ResultMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun moveToMainScreen()
    }

    interface Intent {
        data class GoToMain(
            val code:String
        ):Intent
        data class ClickDigit(val digit:String): Intent
        object ClickDelete: Intent
    }
}