package uz.gita.presentation.auth.repin

import android.graphics.Color
import org.orbitmvi.orbit.ContainerHost

interface RePinContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val errorAnim:Long = 0,
        val fourDigitCorrect:Boolean = true
    )

    sealed interface SideEffect {
        data class ResultMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun moveToMainScreen()
        suspend fun moveBack()
    }

    interface Intent {
        object SelectBack:Intent
        data class GoToMain(
            val code1:String,
            val code2:String
        ):Intent
    }
}