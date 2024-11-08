package uz.gita.presentation.cards.settingsCard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.common.data.CardData
import uz.gita.common.other.toParams
import uz.gita.domain.cardUseCase.GetCardsUC
import uz.gita.domain.cardUseCase.UpdateCardUC
import uz.gita.common.other.NetworkStatusValidator
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess
import javax.inject.Inject

@HiltViewModel
class SettingsCardVM @Inject constructor(
    private val directions: SettingsCardContract.Direction,
    private val getCardsUC: GetCardsUC,
    private val updateCardUC: UpdateCardUC,
    private val networkStatusValidator: NetworkStatusValidator
) : ViewModel(), SettingsCardContract.ViewModel {
    private lateinit var initCards: List<CardData.CardInfo>

    override val container = container<SettingsCardContract.UIState, SettingsCardContract.SideEffect>(
        SettingsCardContract.UIState()
    )

    init {
        getCardsUC.invoke()
            .onStart { intent { reduce { state.copy(cardLoading = true) } } }
            .onSuccess {
                initCards = it
                intent { reduce { state.copy(myCards = it) } }
            }
            .onFailure {
                Log.d("TAG", "SettingsCardVM: $it")
            }
            .onCompletion { intent { reduce { state.copy(cardLoading = true) } } }
            .launchIn(viewModelScope)

    }

    override fun onEventDispatcher(intent: SettingsCardContract.Intent) = intent {
        when (intent) {
            is SettingsCardContract.Intent.ToggleClick -> {
                val newList = state.myCards.mapIndexed { index, card ->
                    if (index == intent.index) card.copy(isVisible = intent.isVisible.toString()) else card
                }
                reduce { state.copy(myCards = newList) }
            }

            SettingsCardContract.Intent.YesClick -> {
                if (networkStatusValidator.isNetworkEnabled) {
                    var result = true
                    Log.d("TTT", "onEventDispatcher: Boshladik")


                    viewModelScope.launch {
                        // Launch each update request with async to run them concurrently
                        val updateResults = state.myCards.mapIndexed { index, cardInfo ->
                            async {
                                if (cardInfo != initCards[index]) {
                                    updateCardUC(cardInfo.toParams())
                                        .onStart { reduce { state.copy(saveLoading = true) } }
                                        .onSuccess { Log.d("TTT", "onEventDispatcher update: $index") }
                                        .onFailure { result = false }
                                        .onCompletion { reduce { state.copy(saveLoading = false) } }
                                        .collect() // collect the flow to execute it
                                }
                            }
                        }

                        // Wait for all async requests to complete
                        updateResults.awaitAll()
                        Log.d("TTT", "onEventDispatcher: Tugattik")
                        if (result) {
                            directions.goBack()
                        } else {
                            postSideEffect(SettingsCardContract.SideEffect.ToastMessage("Something went wrong\ntry again"))
                        }
                    }



                } else {
                    reduce { state.copy(networkDialog = true) }
                }
            }

            SettingsCardContract.Intent.Back -> {
                if (state.myCards == initCards) {
                    directions.goBack()
                } else {
                    reduce { state.copy(saveDialog = true) }
                }
            }

            is SettingsCardContract.Intent.NetworkDialogDismiss -> {
                if (intent.quit) {
                    reduce { state.copy(saveDialog = false) }
                    directions.goBack()
                } else
                    reduce { state.copy(saveDialog = false) }
            }


            SettingsCardContract.Intent.AddCard -> {
                directions.goAddCard()
            }
        }
    }

}
