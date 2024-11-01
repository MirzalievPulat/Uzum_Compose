package uz.gita.presentation.cards.verifyTransfer

import androidx.compose.runtime.MutableState
import org.orbitmvi.orbit.ContainerHost
import uz.gita.common.data.CardData
import uz.gita.common.data.HomeData
import uz.gita.common.other.CardsType

interface VerifyTransferContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
        val code :MutableState<String>
    }

    data class UIState(
        val networkDialog:Boolean = false,
        val phoneNumber:String = "",
        val sendAgain:Long = 0L,
    )

    sealed interface SideEffect {
        data class ToastMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun goBack()
        suspend fun goDoneScreen()
    }

    interface Intent {
        data class CodeChange(val code:String):Intent
        object Back: Intent
        object NetworkDialogDismiss: Intent
        object SendAgainClick:Intent
    }
}