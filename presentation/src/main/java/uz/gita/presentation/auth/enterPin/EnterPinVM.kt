package uz.gita.presentation.auth.enterPin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.common.data.HomeData
import uz.gita.domain.authUseCase.GetNameUC
import uz.gita.domain.authUseCase.GetPinUC
import uz.gita.domain.authUseCase.SetPinUC
import uz.gita.domain.homeUseCase.GetBasicInfoUC
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess
import javax.inject.Inject

@HiltViewModel
class EnterPinVM @Inject constructor(
    private val directions: EnterPinContract.Direction,
    private val getPinUC: GetPinUC,
    private val getNameUC: GetNameUC
) : ViewModel(), EnterPinContract.ViewModel {
    private var code = "0000"
    private var name = "Po'lat"

    init {
        getNameUC()
            .onSuccess {
                name = it
            }
            .onFailure {
                Log.d("TAG", "getBasicInfoUC: error: ${it.message}")
            }
            .launchIn(viewModelScope)

        getPinUC()
            .onSuccess {
                code = it
            }
            .onFailure {
                Log.d("TAG", "getBasicInfoUC: error: ${it.message}")
            }
            .launchIn(viewModelScope)
    }

    override val container = container<EnterPinContract.UIState,
            EnterPinContract.SideEffect>(EnterPinContract.UIState().copy(name = name))


    override fun onEventDispatcher(intent: EnterPinContract.Intent) = intent {
        when (intent) {
            EnterPinContract.Intent.ClickDelete -> {
                if (state.currentCode.isNotBlank()) {
                    val temp = state.currentCode.substring(0, state.currentCode.lastIndex)
                    reduce { state.copy(currentCode = temp) }
                }
            }

            is EnterPinContract.Intent.ClickDigit -> {
                if (state.currentCode.length < 4) {
                    val newCode = state.currentCode + intent.digit
                    reduce { state.copy(currentCode = newCode) }
                    handlePinCodeChange(newCode)
                }
            }
        }
    }

    private fun handlePinCodeChange(newCode: String) = intent {
        if (newCode.length == 4) {
            if (code == newCode) {
                reduce { state.copy(fourDigitCorrect = true) }
                delay(150)
                directions.moveToMainScreen()
            } else {
                viewModelScope.launch {
                    reduce { state.copy(errorAnim = System.currentTimeMillis(), fourDigitCorrect = false) }
                    delay(500)
                    reduce { state.copy(currentCode = "") }
                }
            }

        }
    }
}