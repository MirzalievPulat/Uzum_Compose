package uz.gita.uzumcompose.presentation.auth.pin

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class PinVM @Inject constructor(
    private val directions: PinContract.Direction
) : ViewModel(), PinContract.ViewModel {

    override val container =
        container<PinContract.UIState, PinContract.SideEffect>(
            PinContract.UIState()
        )


    override fun onEventDispatcher(intent: PinContract.Intent) = intent {
        when (intent) {
            is PinContract.Intent.GoNextScreen -> {
                directions.moveToRePinScreen(intent.code1)
            }
        }
    }
}