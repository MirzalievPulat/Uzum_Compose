package uz.gita.presentation.cards.updateCard

import org.orbitmvi.orbit.ContainerHost
import uz.gita.common.data.CardData

interface UpdateCardContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
        fun setCardParam(cardInfo: CardData.CardInfo)
    }

    data class UIState(
        val isBottomSheetOpen:Boolean = false,
        val networkDialog:Boolean = false,
        val updateDialog:Boolean = false,
        val deleteLoading:Boolean = false,
        val updateLoading:Boolean = false,
        val nameFocused:Boolean = false,

        val isVisible: Boolean = false,
        val name:String = "",
        val theme:String = "",
    )

    sealed interface SideEffect {
        data class ToastMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun goBack()
        suspend fun goHome()
    }

    interface Intent {
        object DeleteClick: Intent
        object BottomSheetDismiss: Intent
        data class DeleteYesClick(
            val id:String,
        ): Intent

        object UpdateYesClick:Intent
        object UpdateNoClick:Intent
        object UpdateDismissClick:Intent

        object Back: Intent
        object NetworkDialogDismiss: Intent
        object NameClick:Intent
        data class IsVisibleClick(val isVisible:Boolean):Intent
        data class ThemeClick(val theme:String):Intent
        data class NameChange(val name:String):Intent
    }
}