package uz.gita.presentation.menu.addCard

import org.orbitmvi.orbit.ContainerHost
import uz.gita.common.data.CardData

interface AddCardContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val dialogOpen: Boolean = false,
        val buttonEnabled:Boolean = false
    )

    sealed interface SideEffect {
        data class ToastMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun goBack()
    }

    interface Intent {
        data class NextClick(
            val pan:String,
            val expiredMonty:String,
            val expiredYear:String,
            val name:String
        ):Intent
    }
}