package uz.gita.presentation.cards.verifyTransfer

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
import uz.gita.domain.transferUseCase.TransferResendUC
import uz.gita.domain.transferUseCase.TransferVerifyUC
import uz.gita.presentation.helper.NetworkStatusValidator
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess
import javax.inject.Inject

@HiltViewModel
class VerifyTransferVM @Inject constructor(
    private val directions: VerifyTransferContract.Direction,
    private val transferVerifyUC: TransferVerifyUC,
    private val transferResendUC: TransferResendUC,
    private val networkStatusValidator: NetworkStatusValidator
) : ViewModel(), VerifyTransferContract.ViewModel {

    override val container = container<VerifyTransferContract.UIState, VerifyTransferContract.SideEffect>(
        VerifyTransferContract
            .UIState()
    )

    override var code: MutableState<String> = mutableStateOf("")


    override fun onEventDispatcher(intent: VerifyTransferContract.Intent) = intent {
        when (intent) {

            is VerifyTransferContract.Intent.CodeChange -> {
                Log.d("TAG", "onEventDispatcher: ${intent.code}")
                if (intent.code.length <= 6 && !intent.code.contains(Regex("[ .,\\-]"))){
                    code.value = intent.code

                    if (intent.code.length == 6) {
                        Log.d("TAG", "check code: ${code.value}")
                        transferVerifyUC(TransferData.TransferCode(code.value))
                            .onStart { }
                            .onSuccess {
                                directions.goDoneScreen()
                            }
                            .onFailure {
                                intent { postSideEffect(VerifyTransferContract.SideEffect.ToastMessage("Incorrect code")) }
                                directions.goBack()
                            }
                            .onFailure { }
                            .launchIn(viewModelScope)

                }


                }
            }

            VerifyTransferContract.Intent.Back -> {
                directions.goBack()
            }

            VerifyTransferContract.Intent.NetworkDialogDismiss -> {
                reduce { state.copy(networkDialog = false) }
            }

            VerifyTransferContract.Intent.SendAgainClick -> {
                if (networkStatusValidator.isNetworkEnabled){
                    transferResendUC()
                        .onSuccess {
                            reduce { state.copy(sendAgain = System.currentTimeMillis(),) }
                        }
                        .onFailure {
                            postSideEffect(VerifyTransferContract.SideEffect.ToastMessage(it.message!!))
                        }
                        .launchIn(viewModelScope)
                }else{
                    reduce { state.copy(networkDialog = true) }
                }
            }
        }
    }


}
