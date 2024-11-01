package uz.gita.presentation.cards.doneTransfer

import androidx.compose.runtime.MutableState
import org.orbitmvi.orbit.ContainerHost
import uz.gita.common.data.CardData
import uz.gita.common.data.HomeData
import uz.gita.common.other.CardsType

interface DoneTransferContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val pan:String = "",
        val sum:String = "",
    )

    sealed interface SideEffect {
        data class ToastMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun goMainScreen()
    }

    interface Intent {
        object CloseClick: Intent

    }
}