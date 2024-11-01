package uz.gita.presentation.cards.doneTransfer

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.common.data.TransferData
import uz.gita.domain.transferUseCase.GetTransferringCardDetails
import uz.gita.domain.transferUseCase.TransferVerifyUC
import uz.gita.presentation.helper.NetworkStatusValidator
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess
import javax.inject.Inject

@HiltViewModel
class DoneTransferVM @Inject constructor(
    private val directions: DoneTransferContract.Direction,
    private val getTransferringCardDetails: GetTransferringCardDetails
) : ViewModel(), DoneTransferContract.ViewModel {

    override val container = container<DoneTransferContract.UIState, DoneTransferContract.SideEffect>(
        DoneTransferContract
            .UIState()
    )

    init {
        getTransferringCardDetails()
            .onSuccess { intent { reduce { state.copy(sum = it.first, pan = it.second.takeLast(4)) } } }
            .launchIn(viewModelScope)
    }

    override fun onEventDispatcher(intent: DoneTransferContract.Intent) = intent {
        when (intent) {
            DoneTransferContract.Intent.CloseClick -> {
                directions.goMainScreen()
            }
        }
    }


}
