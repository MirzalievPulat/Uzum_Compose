package uz.gita.presentation.menu.addCard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.common.data.CardData
import uz.gita.domain.cardUseCase.AddCardUC
import uz.gita.domain.cardUseCase.GetCardsUC
import uz.gita.domain.homeUseCase.GetTotalBalanceUC
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess
import javax.inject.Inject

@HiltViewModel
class AddCardVM @Inject constructor(
    private val addCardUC: AddCardUC,
    private val directions: AddCardContract.Direction,
) : ViewModel(), AddCardContract.ViewModel {

    override val container = container<AddCardContract.UIState, AddCardContract.SideEffect>(AddCardContract.UIState())

    override fun onEventDispatcher(intent: AddCardContract.Intent) = intent {
        when (intent) {
            is AddCardContract.Intent.NextClick -> {
                addCardUC.invoke(
                    CardData.NewCardParams(
                        pan = intent.pan,
                        expiredMonth = intent.expiredMonty,
                        expiredYear = intent.expiredYear,
                        name = intent.name
                    )
                )
                    .onSuccess { directions.goBack() }
                    .onFailure { Log.d("TAG", "onEventDispatcher: ${it.message}") }
                    .launchIn(viewModelScope)
            }
        }
    }

}