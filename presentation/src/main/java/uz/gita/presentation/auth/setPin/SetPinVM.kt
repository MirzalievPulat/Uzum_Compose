package uz.gita.presentation.auth.setPin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.domain.authUseCase.SetPinUC
import javax.inject.Inject

@HiltViewModel
class SetPinVM @Inject constructor(
    private val directions: SetPinContract.Direction,
    private val setPinUC: SetPinUC
) : ViewModel(), SetPinContract.ViewModel {
    var code1 = ""

    override val container = container<SetPinContract.UIState, SetPinContract.SideEffect>(SetPinContract.UIState())

    override fun onEventDispatcher(intent: SetPinContract.Intent) = intent {
        when (intent) {
            SetPinContract.Intent.ClickBack -> {
                code1 = ""
                reduce { state.copy(isSecondTime = false, fourDigitCorrect = true, currentCode = "") }
            }

            SetPinContract.Intent.ClickDelete -> {
                if (state.currentCode.isNotBlank()) {
                    val temp = state.currentCode.substring(0, state.currentCode.lastIndex)
                    reduce { state.copy(currentCode = temp) }
                }
            }

            is SetPinContract.Intent.ClickDigit -> {
                if (state.currentCode.length < 4) {
                    val newCode = state.currentCode + intent.digit
                    reduce { state.copy(currentCode = newCode) }
                    handlePinCodeChange(newCode)
                }
            }
        }
    }

    private fun handlePinCodeChange(newCode: String) = intent{
        if (newCode.length == 4) {
            if (state.isSecondTime) {
                if (code1 == newCode) {
                    reduce { state.copy(fourDigitCorrect = true) }
                    setPinUC(code1).launchIn(viewModelScope)
                    delay(150)
                    Log.d("TAG", "handlePinCodeChange: setPinVM code:$code1")
                    directions.moveToMainScreen()
                } else {
                    viewModelScope.launch {
                        reduce { state.copy(errorAnim = System.currentTimeMillis(), fourDigitCorrect = false) }
                        delay(500)
                        reduce { state.copy(currentCode = "") }
                    }
                }
            } else {
                viewModelScope.launch {
                    delay(150)
                    code1 = newCode
                    reduce { state.copy(currentCode = "", isSecondTime = true) }
                }
            }
        }
    }

}