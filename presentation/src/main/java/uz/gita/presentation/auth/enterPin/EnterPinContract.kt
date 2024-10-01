package uz.gita.presentation.auth.enterPin

import android.graphics.Color
import org.orbitmvi.orbit.ContainerHost

interface EnterPinContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val errorAnim:Long = 0,
        val fourDigitCorrect:Boolean = true
    )

    sealed interface SideEffect {
        data class Message(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun moveToMainScreen()
    }

    interface Intent {
        object SelectBack:Intent
        data class GoToMain(
            val code1:String,
            val code2:String
        ):Intent
    }
}