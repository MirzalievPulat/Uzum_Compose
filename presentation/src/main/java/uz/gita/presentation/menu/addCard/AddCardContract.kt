package uz.gita.presentation.menu.addCard

import org.orbitmvi.orbit.ContainerHost
import uz.gita.common.data.CardData

interface AddCardContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val dialogOpen: Boolean = false,
//        val buttonEnabled:Boolean = false,
        val isLoading:Boolean = false,
        val networkDialog:Boolean = false,
        val errorPan:String? = null,
        val errorMonthYear:String? = null,
        val errorName:String? = null,
    )

    sealed interface SideEffect {
        data class ToastMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun goBack()
        suspend fun goHome()
    }

    interface Intent {
        data class NextClick(
            val pan:String,
            val expiredMontyYear:String,
            val name:String
        ):Intent
        object Back:Intent
        object DialogOKClick:Intent
        object NetworkDialogDismiss:Intent
    }
}