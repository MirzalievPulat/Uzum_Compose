package uz.gita.presentation.cards.transferFromTo

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.common.data.RecentTransferData
import uz.gita.common.data.TransferData
import uz.gita.domain.cardUseCase.GetCardsUC
import uz.gita.domain.transferUseCase.GetFeeUC
import uz.gita.domain.transferUseCase.GetTransferringCardDetails
import uz.gita.domain.transferUseCase.SaveLastTransferredCardsUC
import uz.gita.domain.transferUseCase.SaveTransferringCardDetailsUC
import uz.gita.domain.transferUseCase.TransferUC
import uz.gita.common.other.NetworkStatusValidator
import uz.gita.presentation.helper.extensions.formatToMoney
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess
import javax.inject.Inject

@HiltViewModel
class TransferFromToVM @Inject constructor(
    private val directions: TransferFromToContract.Direction,
    private val getCardsUC: GetCardsUC,
    private val transferUC: TransferUC,
    private val getFeeUC: GetFeeUC,
    private val saveLastTransferredCardsUC: SaveLastTransferredCardsUC,
    private val saveTransferringCardDetailsUC: SaveTransferringCardDetailsUC,
    private val getTransferringCardDetailsUC: GetTransferringCardDetails,
    private val networkStatusValidator: NetworkStatusValidator
) : ViewModel(), TransferFromToContract.ViewModel {

    override val container = container<TransferFromToContract.UIState, TransferFromToContract.SideEffect>(
        TransferFromToContract
            .UIState()
    )
    override val sum: MutableState<String> = mutableStateOf("")

    init {
        getCardsUC.invoke()
            .onStart { }
            .onSuccess {
                intent {
                    reduce { state.copy(myCards = it, fromCard = it.first()) }
                }
            }
            .onFailure {
                Log.d("TAG", "From to screen get Cards failure: $it")
//                intent { postSideEffect(TransferFromToContract.SideEffect.ToastMessage(it.message!!)) }
                intent { reduce { state.copy(networkDialog = true) } }
            }
            .onCompletion { }
            .launchIn(viewModelScope)

    }

    override fun onEventDispatcher(intent: TransferFromToContract.Intent) = intent {
        when (intent) {
            is TransferFromToContract.Intent.SumChange -> {
                println(intent.sum)
                if (intent.sum.length <= 10 && !intent.sum.contains(Regex("[ .,\\-]")) && !intent.sum.startsWith("0")) {
                    sum.value = intent.sum

                    if (networkStatusValidator.isNetworkEnabled) {
                        if (sum.value.isNotBlank()) {
                            val sumWithCommission = sum.value.toLong() + (sum.value.toLong() * 0.01).toLong()
                            if (sumWithCommission <= state.fromCard.amount.toLong()) {
                                val sumFormatted = formatToMoney(sumWithCommission)
                                reduce {
                                    state.copy(
                                        sumLoading = true,
                                        errorSum = Pair("To be debited: $sumFormatted sum\nCommission 1%", false)
                                    )
                                }
                            } else {
                                reduce { state.copy(errorSum = Pair("Not enough money", true)) }
                            }
                        } else {
                            reduce { state.copy(errorSum = Pair("Commission will be shown after entering the amount", false)) }
                        }

//                        getFeeUC.invoke(
//                            TransferData.FeeForThis(state.fromCard.id, state.toCard.second, sum.value)
//                        )
//                            .onStart { reduce { state.copy(sumLoading = true) } }
//                            .onSuccess {
//                                reduce {
//                                    state.copy(
//                                        errorSum = Pair(
//                                            "To be debited: ${it.amount} sum\nComission ${it.fee}%",
//                                            false
//                                        )
//                                    )
//                                }
//                            }
//                            .onFailure {
//                                reduce { state.copy(errorSum = Pair(it.message!!, true)) }
//                            }
//                            .onCompletion { delay(500); reduce { state.copy(sumLoading = false) } }
//                            .launchIn(viewModelScope)

                        delay(500)
                        reduce { state.copy(sumLoading = false) }
                    } else {
                        reduce { state.copy(errorSum = Pair("Network error", true)) }
                    }
                }


            }

            is TransferFromToContract.Intent.CardChoose -> {
                reduce { state.copy(fromCard = intent.cardInfo, cardsBottomSheet = false) }

                if (sum.value.isNotBlank()) {
                    if (sum.value.toLong() <= state.fromCard.amount.toLong()) {
                        val sumWithCommission = formatToMoney(sum.value.toLong() + (sum.value.toLong() * 0.01).toLong())
                        reduce { state.copy(errorSum = Pair("To be debited: $sumWithCommission sum\nCommission 1%", false)) }
                    } else {
                        reduce { state.copy(errorSum = Pair("Not enough money", true)) }
                    }
                }


            }

            TransferFromToContract.Intent.TransferClick -> {
                if (state.fromCard.amount.toInt() >= sum.value.toInt()) {
                    if (networkStatusValidator.isNetworkEnabled) {
                        val type = if (state.myCards.any { it.pan == state.toCard.second }) "own-card" else "third-card"
                        transferUC(
                            TransferData.Transfer(
                                type = type,
                                senderId = state.fromCard.id,
                                receiverPan = state.toCard.second,
                                amount = sum.value
                            )
                        )
                            .onStart { reduce { state.copy(transferLoading = true) } }
                            .onSuccess {

                                saveLastTransferredCardsUC(
                                    RecentTransferData(0,state.toCard.first,state.toCard.second,System.currentTimeMillis())
                                ).launchIn(viewModelScope)

                                saveTransferringCardDetailsUC(sum.value, state.toCard.second)
                                    .launchIn(viewModelScope)
                                directions.goVerify()
                            }
                            .onFailure {
                                reduce { state.copy(errorSum = Pair(it.message!!, true)) }
                            }
                            .onCompletion { reduce { state.copy(transferLoading = false) } }
                            .launchIn(viewModelScope)
                    } else {
                        reduce { state.copy(networkDialog = true) }
                    }
                }
            }

            TransferFromToContract.Intent.FromCardChoose -> {
                reduce { state.copy(cardsBottomSheet = true) }
            }

            TransferFromToContract.Intent.NetworkDialogDismiss -> {
                reduce { state.copy(networkDialog = false) }
            }

            TransferFromToContract.Intent.BottomSheetClose -> {
                reduce { state.copy(cardsBottomSheet = false) }
            }

            TransferFromToContract.Intent.Back -> {
                directions.goBack()
            }


            TransferFromToContract.Intent.AddCardClick -> {
                reduce { state.copy(cardsBottomSheet = false) }
                directions.goAddCard()
            }
        }
    }

    override fun setToCard(pair: Pair<String, String>) {
        intent {
            val filteredCards = state.myCards.filter { it.pan != pair.second.takeLast(4) }
            reduce { state.copy(myCards = filteredCards, fromCard = filteredCards.first(), toCard = pair) }
        }
    }

}
