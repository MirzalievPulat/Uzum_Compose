package uz.gita.presentation.auth.signInVerify

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.common.data.AuthData
import uz.gita.domain.authUseCase.SignInResendUC
import uz.gita.domain.authUseCase.SignInVerifyUC
import uz.gita.presentation.helper.NetworkStatusValidator
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class SignInVerifyVM @Inject constructor(
    private val signInVerifyUC: SignInVerifyUC,
    private val resendUC: SignInResendUC,
    private val directions: SignInVerifyContract.Direction,
    private val networkStatusValidator: NetworkStatusValidator
) : ViewModel(), SignInVerifyContract.ViewModel {

    override val container = container<SignInVerifyContract.UIState, SignInVerifyContract.SideEffect>(
        SignInVerifyContract.UIState().copy(networkStatusValidator = networkStatusValidator)
    )

    override fun onEventDispatcher(intent: SignInVerifyContract.Intent) = intent {
        when (intent) {
            SignInVerifyContract.Intent.SelectResend -> {
                if (networkStatusValidator.isNetworkEnabled) {
                    resendUC.invoke()
                        .onStart { reduce { state.copy(showProgress = true) } }
                        .onSuccess {
                            reduce { state.copy(resendCode = Random.nextFloat(), codeError = null) }
                        }
                        .onFailure { postSideEffect(SignInVerifyContract.SideEffect.Message(it.message ?: "Error happened")) }
                        .onCompletion { reduce { state.copy(showProgress = false) } }
                        .launchIn(viewModelScope)
                } else {
                    reduce { state.copy(networkError = true) }
                }
            }

            SignInVerifyContract.Intent.SelectBack -> {
                directions.moveBack()
            }

            SignInVerifyContract.Intent.DismissDialog -> {
                reduce { state.copy(networkError = false) }
            }

            is SignInVerifyContract.Intent.GoToPin -> {
                if (networkStatusValidator.isNetworkEnabled) {
                    signInVerifyUC.invoke(
                        AuthData.Verify(
                            intent.code
                        )
                    ).onStart { }
                        .onSuccess {
                            directions.moveToPinScreen()
                        }
                        .onFailure {
                            Log.d("TAG", "onEventDispatcher signin verify error: ${it.message}")
                            if (it.message == "Code noto'g'ri")
                                reduce { state.copy(codeError = "Wrong code") }
                            else
                                reduce { state.copy(codeError = "Code entry timed out") }

                        }
                        .onCompletion { }
                        .launchIn(viewModelScope)
                } else {
                    reduce { state.copy(networkError = true) }
                }

            }

        }
    }

}