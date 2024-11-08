package uz.gita.presentation.history

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.common.data.HomeData
import uz.gita.domain.transferUseCase.GetHistoryPagingUC
import uz.gita.common.other.NetworkStatusValidator
import javax.inject.Inject

@HiltViewModel
class HistoryVM @Inject constructor(
    private val getHistoryPagingUC: GetHistoryPagingUC,
    private val direction: HistoryContract.Direction,
    private val networkStatusValidator: NetworkStatusValidator
) : ViewModel(), HistoryContract.ViewModel {
    private lateinit var initInfo: HomeData.FullInfo

    override val container = container<HistoryContract.UIState, HistoryContract.SideEffect>(HistoryContract.UIState())

    init {
       update()
    }

    override fun onEventDispatcher(intent: HistoryContract.Intent) = intent {
        when (intent) {
            HistoryContract.Intent.NetworkDismiss -> {
                reduce { state.copy(networkDialog = false) }
            }

            HistoryContract.Intent.Back -> {
                direction.goBack()
            }

            HistoryContract.Intent.UpdateClick -> {
                reduce { state.copy(isLoading = true) }
                update()
            }
        }
    }

    private fun update() {
        Log.d("TAG", "update: ishlab ketyapti")

        if (networkStatusValidator.isNetworkEnabled) {
            intent {
                reduce { state.copy(history = getHistoryPagingUC())}
                delay(500)
                reduce { state.copy(isLoading = false) }
            }
        } else {
            intent { reduce { state.copy(networkDialog = true, isLoading = false) } }
        }
    }


}