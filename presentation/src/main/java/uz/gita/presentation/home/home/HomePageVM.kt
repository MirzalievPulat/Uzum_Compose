package uz.gita.presentation.home.home

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
import uz.gita.domain.cardUseCase.GetCardsUC
import uz.gita.domain.homeUseCase.GetTotalBalanceUC
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess
import javax.inject.Inject

@HiltViewModel
class HomePageVM @Inject constructor(
    private val getTotalBalanceUC: GetTotalBalanceUC,
    private val getCardsUC: GetCardsUC,
    private val directions: HomePageContract.Direction,
) : ViewModel(), HomePageContract.ViewModel {

    override val container = container<HomePageContract.UIState, HomePageContract.SideEffect>(HomePageContract.UIState())

    init {
        updateScreen()
    }


    override fun onEventDispatcher(intent: HomePageContract.Intent) = intent {
        when (intent) {
            HomePageContract.Intent.AddCardClick -> {
                directions.goAddCardScreen()
            }
            HomePageContract.Intent.UpdateClick -> {
                updateScreen()
            }

        }
    }

    private fun updateScreen(){
        getTotalBalanceUC()
            .onStart {  }
            .onSuccess { intent { reduce { state.copy(totalBalance = it.balance) } } }
            .onFailure { Log.d("TAG", "onEventDispatcher: ${it.message}") }
            .onCompletion {  }
            .launchIn(viewModelScope)

        getCardsUC()
            .onSuccess { intent { reduce { state.copy(cardList = it) } } }
            .launchIn(viewModelScope)
    }
}