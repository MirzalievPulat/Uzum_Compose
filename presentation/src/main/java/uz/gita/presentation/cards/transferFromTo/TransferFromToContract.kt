package uz.gita.presentation.cards.transferFromTo

import androidx.compose.runtime.MutableState
import org.orbitmvi.orbit.ContainerHost
import uz.gita.common.data.CardData
import uz.gita.common.data.HomeData
import uz.gita.common.other.CardsType

interface TransferFromToContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
        val sum :MutableState<String>
        fun setToCard(pair: Pair<String,String>)
    }

    data class UIState(
        val networkDialog:Boolean = false,
        val sumLoading:Boolean = false,
        val notEnoughMoneyDialog:Boolean = false,
        val transferLoading:Boolean = false,
        val cardsBottomSheet:Boolean = false,
        val errorSum:Pair<String,Boolean> = Pair("Commission will be shown after entering the amount",false),
        val fromCard:CardData.CardInfo = CardData.CardInfo("","","10000","Mirzaliyev","9860986098609860"),
        val toCard:Pair<String,String> = Pair("Olimboy Olimboyev", "9860986098609860"),
        val myCards:List<CardData.CardInfo> = listOf(
            CardData.CardInfo("","","100000","Polat","9898"),
            CardData.CardInfo("","","100000","Polat","9898"),
            CardData.CardInfo("","","100000","Polat","9898"),
        ),
    )

    sealed interface SideEffect {
        data class ToastMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun goBack()
        suspend fun goVerify()
        suspend fun goAddCard()
    }

    interface Intent {
//        data class CardClick(
//            val name:String,
//            val pan:String,
//        ): Intent
        data class SumChange(
            val sum:String
        ):Intent
        data class CardChoose(
            val cardInfo: CardData.CardInfo
        ):Intent
        object Back: Intent
        object NetworkDialogDismiss: Intent
        object FromCardChoose:Intent
        object AddCardClick:Intent
        object TransferClick:Intent
        object BottomSheetClose:Intent
    }
}