package uz.gita.presentation.cards.updateCard

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
import uz.gita.domain.cardUseCase.DeleteCardUC
import uz.gita.domain.cardUseCase.UpdateCardUC
import uz.gita.presentation.helper.NetworkStatusValidator
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess
import javax.inject.Inject

@HiltViewModel
class UpdateCardVM @Inject constructor(
    private val updateCardUC: UpdateCardUC,
    private val deleteCardUC: DeleteCardUC,
    private val directions: UpdateCardContract.Direction,
    private val networkStatusValidator: NetworkStatusValidator
) : ViewModel(), UpdateCardContract.ViewModel {
    private lateinit var initCard:CardData.UpdateCardParams
//back qilish 2 xil usuli bor ekan?
    override val container = container<UpdateCardContract.UIState,
            UpdateCardContract.SideEffect>(UpdateCardContract.UIState())


    override fun onEventDispatcher(intent: UpdateCardContract.Intent) = intent {
        when (intent) {
            is UpdateCardContract.Intent.DeleteYesClick -> {

                if (networkStatusValidator.isNetworkEnabled) {
                    deleteCardUC.invoke(
                        intent.id
                    ).onStart { reduce { state.copy(deleteLoading = true) } }
                        .onSuccess {
                            directions.goBack()
                        }
                        .onFailure {
                            postSideEffect(UpdateCardContract.SideEffect.ToastMessage(it.message ?: "Unknown error"))
                        }
                        .onCompletion {
                            reduce { state.copy(deleteLoading = false) }
                            reduce { state.copy(isBottomSheetOpen = false) }
                        }
                        .launchIn(viewModelScope)
                } else {
                    reduce { state.copy(networkDialog = true) }
                }
            }

            is UpdateCardContract.Intent.UpdateYesClick->{
                if (networkStatusValidator.isNetworkEnabled) {
                    updateCardUC.invoke(
                        CardData.UpdateCardParams(initCard.id,state.name,state.theme,state.isVisible.toString())
                    ).onStart { reduce { state.copy(updateLoading = true) } }
                        .onSuccess {
                            directions.goHome()
                        }
                        .onFailure {
                            postSideEffect(UpdateCardContract.SideEffect.ToastMessage(it.message ?: "Unknown error"))
                        }
                        .onCompletion { reduce { state.copy(updateLoading = false) } }
                        .launchIn(viewModelScope)
                } else {
                    reduce { state.copy(networkDialog = true) }
                }
            }

            UpdateCardContract.Intent.UpdateNoClick->{
                reduce { state.copy(updateDialog = false) }
                directions.goHome()
            }

            UpdateCardContract.Intent.UpdateDismissClick->{
                reduce { state.copy(updateDialog = false) }
            }

            UpdateCardContract.Intent.Back -> {
                val lastCard = CardData.UpdateCardParams(initCard.id,state.name,state.theme,state.isVisible.toString())
                if (initCard == lastCard){
                    directions.goHome()
                }else{
                    reduce{ state.copy(updateDialog = true) }
                }
            }

            UpdateCardContract.Intent.DeleteClick -> {
                reduce { state.copy(isBottomSheetOpen = true) }
            }

            UpdateCardContract.Intent.BottomSheetDismiss -> {
                reduce { state.copy(isBottomSheetOpen = false) }
            }

            UpdateCardContract.Intent.NetworkDialogDismiss -> {
                reduce { state.copy(networkDialog = false) }
            }

            UpdateCardContract.Intent.NameClick -> {
                if (state.nameFocused){
                    reduce { state.copy(nameFocused = false) }
                }else{
                    Log.d("TAG", "onEventDispatcher: nameClick to true")
                    reduce { state.copy(nameFocused = true) }
                }
            }

            is UpdateCardContract.Intent.IsVisibleClick->{
                reduce { state.copy(isVisible = intent.isVisible) }
            }

            is UpdateCardContract.Intent.NameChange->{
                reduce { state.copy(name = intent.name) }
            }

            is UpdateCardContract.Intent.ThemeClick->{
                reduce { state.copy(theme = intent.theme) }
            }


        }
    }

    override fun setCardParam(cardInfo: CardData.CardInfo) {
        initCard = CardData.UpdateCardParams(cardInfo.id,cardInfo.name,cardInfo.themeType,cardInfo.isVisible)
        intent { reduce { state.copy(
            isVisible = cardInfo.isVisible.toBoolean(),
            name = cardInfo.name,
            theme = cardInfo.themeType
        )
        } }
    }


}