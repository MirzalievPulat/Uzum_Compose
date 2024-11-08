package uz.gita.presentation.cards.addCard

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
import uz.gita.domain.cardUseCase.AddCardUC
import uz.gita.domain.validatorUseCase.CardPanValidatorUC
import uz.gita.domain.validatorUseCase.FirstNameValidatorUC
import uz.gita.domain.validatorUseCase.YearValidatorUC
import uz.gita.common.other.NetworkStatusValidator
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess
import javax.inject.Inject

@HiltViewModel
class AddCardVM @Inject constructor(
    private val addCardUC: AddCardUC,
    private val directions: AddCardContract.Direction,
    private val cardPanValidatorUC: CardPanValidatorUC,
    private val yearValidatorUC: YearValidatorUC,
    private val firstNameValidatorUC: FirstNameValidatorUC,
    private val networkStatusValidator: NetworkStatusValidator
) : ViewModel(), AddCardContract.ViewModel {

    override val container = container<AddCardContract.UIState, AddCardContract.SideEffect>(AddCardContract.UIState())

    override fun onEventDispatcher(intent: AddCardContract.Intent) = intent {
        when (intent) {
            is AddCardContract.Intent.NextClick -> {

                if (networkStatusValidator.isNetworkEnabled){
                    if(!areInputsValid(intent)) return@intent

                    addCardUC.invoke(
                        CardData.NewCardParams(
                            pan = intent.pan,
                            expiredMonth = intent.expiredMontyYear.substring(0,2),
                            expiredYear = "20"+intent.expiredMontyYear.substring(2),
                            name = intent.name
                        )
                    )
                        .onStart { reduce { state.copy(isLoading = true) } }
                        .onSuccess { reduce { state.copy(dialogOpen = true) } }
                        .onFailure {
                            postSideEffect(AddCardContract.SideEffect.ToastMessage(it.message ?: "Unknown error"))
                        }
                        .onCompletion { reduce { state.copy(isLoading = false) } }
                        .launchIn(viewModelScope)
                }else{
                    reduce { state.copy(networkDialog = true) }
                }

            }
            AddCardContract.Intent.Back ->{
                directions.goBack()
            }
            AddCardContract.Intent.DialogOKClick ->{
                reduce { state.copy(dialogOpen = false) }
                directions.goHome()
            }
            AddCardContract.Intent.NetworkDialogDismiss->{
                reduce { state.copy(networkDialog = false) }
            }
        }
    }

    private fun areInputsValid(intent: AddCardContract.Intent.NextClick): Boolean {
        val pan = cardPanValidatorUC(intent.pan)
        val monthYear = yearValidatorUC(intent.expiredMontyYear)
        val name = firstNameValidatorUC(intent.name)

        intent {
            reduce {
                state.copy(
                    errorPan = pan,
                    errorMonthYear = monthYear,
                    errorName = name?.replace("First name","Name")
                )
            }
        }

        return pan == null  && monthYear == null && name == null
    }
}