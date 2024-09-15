package uz.gita.uzumcompose.presentation.auth.repin

import androidx.compose.ui.graphics.Color
import org.orbitmvi.orbit.ContainerHost
import uz.gita.uzumcompose.ui.theme.HintUzum

interface RePinContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val errorAnim:Long = 0,
        val dotsColor:Color = Color.HintUzum
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