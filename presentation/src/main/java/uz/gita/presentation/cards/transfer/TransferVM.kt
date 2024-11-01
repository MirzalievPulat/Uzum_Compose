package uz.gita.presentation.cards.transfer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.common.data.CardData
import uz.gita.common.data.RecentTransferData
import uz.gita.common.data.TransferData
import uz.gita.common.other.CardsType
import uz.gita.domain.cardUseCase.GetCardsUC
import uz.gita.domain.transferUseCase.GetCardOwnerUC
import uz.gita.domain.transferUseCase.GetLastTransferredCardsUC
import uz.gita.presentation.helper.NetworkStatusValidator
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess
import javax.inject.Inject

@HiltViewModel
class TransferVM @Inject constructor(
    private val getCardOwnerUC: GetCardOwnerUC,
    private val directions: TransferContract.Direction,
    private val getCardsUC: GetCardsUC,
    private val getLastTransferredCardsUC: GetLastTransferredCardsUC,
    private val networkStatusValidator: NetworkStatusValidator
) : ViewModel(), TransferContract.ViewModel {
    private lateinit var allMyCards:List<CardData.CardInfo>
    private lateinit var lastTransferredCards:List<RecentTransferData>

    override val container = container<TransferContract.UIState, TransferContract.SideEffect>(TransferContract.UIState())

    init {

        //loadinglar

        getCardsUC.invoke()
            .onStart { intent { reduce { state.copy(myCardsLoading = true) } } }
            .onSuccess {
                allMyCards = it
                intent { reduce { state.copy(myCards = it) } } }
            .onFailure {
                Log.d("TAG", "Polat qara get Cards failure: $it")
                intent { postSideEffect(TransferContract.SideEffect.ToastMessage(it.message!!)) } }
            .onCompletion { intent { reduce { state.copy(myCardsLoading = false) } } }
            .launchIn(viewModelScope)

        getLastTransferredCardsUC.invoke()
            .onStart { intent { reduce { state.copy(recentLoading = true) } } }
            .onSuccess {
               val filtered = it.sortedByDescending { it.time }.distinctBy { it.pan }

                lastTransferredCards = filtered
                intent { reduce { state.copy(lastTransferredCards = filtered) } } }
            .onFailure {
                Log.d("TAG", "Polat qara last tarnsferlar failure: $it")
                intent { postSideEffect(TransferContract.SideEffect.ToastMessage(it.message!!)) } }
            .onCompletion { intent { reduce { state.copy(recentLoading = false) } } }
            .launchIn(viewModelScope)
    }

    override fun onEventDispatcher(intent: TransferContract.Intent) = intent {
        when (intent) {
            is TransferContract.Intent.CardNumberChange -> {
                if (intent.pan.length <= 16)
                    reduce { state.copy(panText = intent.pan) }

                if (intent.pan.isBlank()){
                    reduce { state.copy(cardsType = CardsType.RECENT, myCards = allMyCards, lastTransferredCards = lastTransferredCards) }
                }else{
                    val searchRecent = lastTransferredCards.filter { it.pan.contains(state.panText) }
                    val searchMyCards = allMyCards.filter { it.pan.contains(state.panText) }
                    Log.d("TAG", "onEventDispatcher: searchRecent: $searchRecent  searchMyCards: $searchMyCards")
                    reduce { state.copy(cardsType = CardsType.BY_SEARCH, lastTransferredCards = searchRecent, myCards = searchMyCards) }
                }

                if (networkStatusValidator.isNetworkEnabled && intent.pan.length == 16) {
                    getCardOwnerUC.invoke(
                        TransferData.CardOwnerPan(intent.pan)
                    )
                        .onStart { reduce { state.copy(bySearchLoading = true) } }
                        .onSuccess { reduce { state.copy(foundCard = it.name) } }
                        .onFailure {
                            reduce { state.copy(errorPan = it.message) }
                        }
                        .onCompletion { reduce { state.copy(bySearchLoading = false) } }
                        .launchIn(viewModelScope)
                } else {
                    reduce { state.copy(networkDialog = true) }
                }
            }

            is TransferContract.Intent.CardTypesChange->{
                if (intent.cardsType == CardsType.RECENT){
                    reduce { state.copy(cardsType = CardsType.RECENT) }
                }else{
                    reduce { state.copy(cardsType = CardsType.MY_CARDS) }
                }
            }
            is TransferContract.Intent.CardClick->{
                Log.d("TTT", "CardClick owner: ${intent.name} pan: ${intent.pan}")
                val pan  = if (intent.pan.length == 4) "986008080808"+intent.pan else intent.pan
                directions.goTransferFromTo(intent.name,pan)
            }

            TransferContract.Intent.Back -> {
                directions.goBack()
            }

            TransferContract.Intent.NetworkDialogDismiss -> {
                reduce { state.copy(networkDialog = false) }
            }

            TransferContract.Intent.CameraClick -> {
               directions.goCamera()
            }

            TransferContract.Intent.PanClearClick -> {
                reduce { state.copy(panText = "", cardsType = CardsType.RECENT, myCards = allMyCards, lastTransferredCards =
                lastTransferredCards) }
                Log.d("TAG", "onEventDispatcher: cards: ${state.myCards}")
            }
        }
    }

}