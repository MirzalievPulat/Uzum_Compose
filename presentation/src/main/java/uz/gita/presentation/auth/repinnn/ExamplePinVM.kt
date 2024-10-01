package uz.gita.presentation.auth.repinnn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.domain.authUseCase.SetPinUC
import javax.inject.Inject

@HiltViewModel
class ExamplePinVM @Inject constructor(
//    private val directions: ExampleContract.Direction,
//    private val setPinUC: SetPinUC
) : ViewModel(), ExampleContract.ViewModel {
    var code1 = ""

    override val container = container<ExampleContract.UIState, ExampleContract.SideEffect>(ExampleContract.UIState())

//    init {
//        intent {
//            if (state.currentCode.length != 4) return@intent
//            println("kirdi")
//            if (state.isSecondTime) {
//                if (code1 == state.currentCode) {
//                    reduce { state.copy(fourDigitCorrect = true) }
//                    postSideEffect(ExampleContract.SideEffect.Message("tamom"))
//                } else {
//                    viewModelScope.launch {
//                        reduce { state.copy(errorAnim = System.currentTimeMillis(), fourDigitCorrect = false) }
//                        delay(500)
//                        reduce { state.copy(currentCode = "") }
//                    }
//                }
//            } else {
//                viewModelScope.launch {
//                    delay(100)
//                    code1 = state.currentCode
//                    reduce { state.copy(currentCode = "", isSecondTime = true) }
//
//                }
//
//            }
//        }
//    }

    override fun onEventDispatcher(intent: ExampleContract.Intent) = intent {
        when (intent) {
            ExampleContract.Intent.ClickBack -> {
                code1 = ""
                reduce { state.copy(isSecondTime = false) }
            }

            ExampleContract.Intent.ClickDelete -> {
                if (state.currentCode.isNotBlank()) {
                    val temp = state.currentCode.substring(0, state.currentCode.lastIndex)
                    reduce { state.copy(currentCode = temp) }
                }
            }

            is ExampleContract.Intent.ClickDigit -> {
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
                    postSideEffect(ExampleContract.SideEffect.Message("tamom"))
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