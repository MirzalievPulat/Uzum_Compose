package uz.gita.presentation.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.common.data.HomeData
import uz.gita.domain.homeUseCase.GetFullInfoUC
import uz.gita.domain.homeUseCase.UpdateInfoUC
import uz.gita.domain.transferUseCase.GetHistoryPagingUC
import uz.gita.domain.validatorUseCase.FirstNameValidatorUC
import uz.gita.domain.validatorUseCase.LastNameValidatorUC
import uz.gita.presentation.helper.NetworkStatusValidator
import javax.inject.Inject

@HiltViewModel
class HistoryVM @Inject constructor(
    private val getHistoryPagingUC: GetHistoryPagingUC,
    private val direction: HistoryContract.Direction,
) : ViewModel(), HistoryContract.ViewModel {
    private lateinit var initInfo: HomeData.FullInfo

    override val container = container<HistoryContract.UIState, HistoryContract.SideEffect>(HistoryContract.UIState())

    init {
        intent { reduce { state.copy(history = getHistoryPagingUC()) } }
    }

    override fun onEventDispatcher(intent: HistoryContract.Intent) = intent {
        when (intent) {
            HistoryContract.Intent.NetworkDismiss->{
                reduce { state.copy(networkDialog = false) }
            }

            HistoryContract.Intent.Back->{
                direction.goBack()
            }
        }
    }


}