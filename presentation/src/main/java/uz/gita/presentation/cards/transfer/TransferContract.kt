package uz.gita.presentation.cards.transfer

import org.orbitmvi.orbit.ContainerHost
import uz.gita.common.data.CardData
import uz.gita.common.data.RecentTransferData
import uz.gita.common.other.CardsType

interface TransferContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val recentLoading:Boolean = false,
        val myCardsLoading:Boolean = false,
        val bySearchLoading:Boolean = false,
        val networkDialog:Boolean = false,
        val errorPan:String? = null,
        val panText:String = "",
        val foundCard:String? = null,
        val cardsType: CardsType = CardsType.RECENT,
        val myCards:List<CardData.CardInfo> = listOf(CardData.CardInfo("","","100000","Polat","9898")),
        val lastTransferredCards:List<RecentTransferData> = listOf(RecentTransferData(1,"Polat","9898000011112222",0L)),
    )

    sealed interface SideEffect {
        data class ToastMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun goBack()
        suspend fun goTransferFromTo(name:String,pan:String)
        suspend fun goCamera()
    }

    interface Intent {
        data class CardClick(
            val name:String,
            val pan:String,
        ): Intent
        data class CardNumberChange(
            val pan:String
        ):Intent
        data class CardTypesChange(
            val cardsType: CardsType
        ):Intent
        object Back: Intent
        object NetworkDialogDismiss: Intent
        object CameraClick:Intent
        object PanClearClick:Intent
    }
}