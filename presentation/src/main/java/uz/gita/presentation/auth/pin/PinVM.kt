package uz.gita.presentation.auth.pin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.domain.authUseCase.GetNextScreenUC
import javax.inject.Inject

@HiltViewModel
class PinVM @Inject constructor(
    private val directions: PinContract.Direction,
    private val getNextScreenUC: GetNextScreenUC
) : ViewModel(), PinContract.ViewModel {

    override val container =
        container<PinContract.UIState, PinContract.SideEffect>(
            PinContract.UIState()
        )

    init {
        getNextScreenUC()

            .launchIn(viewModelScope)
    }


    override fun onEventDispatcher(intent: PinContract.Intent) = intent {
        when (intent) {
            is PinContract.Intent.GoNextScreen -> {
                directions.moveToRePinScreen(intent.code1)
            }
        }
    }
}