package uz.gita.presentation.pages.home

import android.util.Log
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
import uz.gita.domain.cardUseCase.GetCardsUC
import uz.gita.domain.homeUseCase.GetIsMoneyVisibleUC
import uz.gita.domain.homeUseCase.GetLastTransfersUC
import uz.gita.domain.homeUseCase.GetTotalBalanceUC
import uz.gita.domain.homeUseCase.SetIsMoneyVisibleUC
import uz.gita.common.other.NetworkStatusValidator
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess
import javax.inject.Inject

@HiltViewModel
class HomePageVM @Inject constructor(
    private val getTotalBalanceUC: GetTotalBalanceUC,
    private val getCardsUC: GetCardsUC,
    private val getLastTransfers:GetLastTransfersUC,
    private val directions: HomePageContract.Direction,
    private val getIsMoneyVisibleUC: GetIsMoneyVisibleUC,
    private val setIsMoneyVisibleUC: SetIsMoneyVisibleUC,
    private val networkStatusValidator: NetworkStatusValidator,
) : ViewModel(), HomePageContract.ViewModel {
    private var firstEntrance = true

    override val container = container<HomePageContract.UIState,
            HomePageContract.SideEffect>(HomePageContract.UIState())

    init {
        Log.d("TAG", "HomePageVm: init ")
        updateScreen()
    }


    override fun onEventDispatcher(intent: HomePageContract.Intent) = intent {
        when (intent) {
            HomePageContract.Intent.UpdateClick -> {
                reduce { state.copy(isLoading = true) }
                updateScreen()
            }
            HomePageContract.Intent.EyeClick -> {
                reduce { state.copy(isMoneyVisible = !state.isMoneyVisible) }
                setIsMoneyVisibleUC(state.isMoneyVisible).launchIn(viewModelScope)
            }
            is HomePageContract.Intent.CardClick->{
                directions.goUpdateCard(intent.cardInfo)
            }
            HomePageContract.Intent.NetworkCloseClick->{
                reduce { state.copy(networkDialog = false) }
            }

            HomePageContract.Intent.ProfileClick->{
                directions.goProfileScreen()
            }

            HomePageContract.Intent.SettingsClick->{
                directions.goSettingsCard()
            }

            HomePageContract.Intent.MonitoringClick->{
                directions.goMonitoring()
            }
        }
    }

    private fun updateScreen(){
        if (networkStatusValidator.isNetworkEnabled || firstEntrance){
            getTotalBalanceUC()
                .onStart {  }
                .onSuccess {
                    Log.d("TAG", "updateScreen: Home pageVM: totalBalance:$it")
                    intent { reduce { state.copy(totalBalance = it.balance) } }
                }
                .onFailure { Log.d("TAG", "homepage updateScreen totalBalance error: ${it.message}") }
                .onCompletion {
                    delay(1000)
                    intent { reduce { state.copy(isLoading = false ) } }
                }
                .launchIn(viewModelScope)

            getIsMoneyVisibleUC()
                .onSuccess { intent { reduce { state.copy(isMoneyVisible = it) } };Log.d("TAG", "updateScreen: isMoneyVisible:$it") }
                .onFailure { Log.d("TAG", "homepage updateScreen  ismoney visible error: ${it.message}") }
                .launchIn(viewModelScope)

            getCardsUC()
                .onSuccess {
                    Log.d("TAG", "updateScreen: Home pageVM: card list:$it")
//                    val visibleCards =  it.filter { it.isVisible.toBoolean() }
                    intent { reduce { state.copy(cardList = it) } }
                }
                .onFailure { Log.d("TAG", "homepage updateScreen card list error: ${it.message}") }
                .launchIn(viewModelScope)

            getLastTransfers()
                .onStart {
                    intent { reduce { state.copy(lastTransfersLoading = true) } }
                }
                .onSuccess {
                    Log.d("TAG", "lastTransfers home page: $it")
                    intent { reduce { state.copy(lastTransfers = it.takeLast(3)) } }
                }
                .onFailure { Log.d("TAG", "homepage updateScreen lastTransfers error: ${it.message}") }
                .onCompletion { intent { reduce { state.copy(lastTransfersLoading = false) } } }
                .launchIn(viewModelScope)

            firstEntrance = false
        }else{
            intent{ reduce { state.copy(networkDialog = true, isLoading = false) } }
        }

    }


}