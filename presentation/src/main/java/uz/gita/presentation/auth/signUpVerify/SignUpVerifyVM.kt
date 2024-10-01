package uz.gita.presentation.auth.signUpVerify

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
import uz.gita.domain.authUseCase.SignUpResendUC
import uz.gita.domain.authUseCase.SignUpVerifyUC
import uz.gita.presentation.auth.signUp.SignUpContract
import uz.gita.presentation.helper.NetworkStatusValidator
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess

import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class SignUpVerifyVM @Inject constructor(
    private val signUpVerifyUC: SignUpVerifyUC,
    private val resendUC: SignUpResendUC,
    private val directions: SignUpVerifyContract.Direction,
    private val networkStatusValidator: NetworkStatusValidator
) : ViewModel(), SignUpVerifyContract.ViewModel {

    override val container =
        container<SignUpVerifyContract.UIState, SignUpVerifyContract.SideEffect>(SignUpVerifyContract.UIState())

    override fun onEventDispatcher(intent: SignUpVerifyContract.Intent) = intent {
        when (intent) {
            SignUpVerifyContract.Intent.SelectResend -> {
                if (networkStatusValidator.isNetworkEnabled){
                    resendUC.invoke()
                        .onStart { reduce { state.copy(showProgress = true) } }
                        .onSuccess { reduce { state.copy(resendCode = Random.nextFloat()) } }
                        .onFailure { postSideEffect(SignUpVerifyContract.SideEffect.Message(it.message?:"Error happened")) }
                        .onCompletion { reduce { state.copy(showProgress = false) } }
                        .launchIn(viewModelScope)
                }else{
                    reduce { state.copy(networkError = true) }
                }

            }

            SignUpVerifyContract.Intent.SelectBack -> {
                directions.moveBack()
            }

            is SignUpVerifyContract.Intent.GoToPin -> {
                if (networkStatusValidator.isNetworkEnabled){
                    signUpVerifyUC.invoke(
                        AuthData.Verify(
                            intent.code
                        )
                    ).onStart {reduce { state.copy(showProgress = true) } }
                        .onSuccess {
                            directions.moveToPinScreen()
                        }
                        .onFailure {
                            postSideEffect(SignUpVerifyContract.SideEffect.Message(it.message?:"Error happened"))
                        }
                        .onCompletion {reduce { state.copy(showProgress = false) } }
                        .launchIn(viewModelScope)
                }else{
                    reduce { state.copy(networkError = true) }
                }

            }

        }
    }

}