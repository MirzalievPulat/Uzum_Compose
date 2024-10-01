package uz.gita.presentation.auth.signInVerify

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
import uz.gita.presentation.auth.signUpVerify.SignUpVerifyContract
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
        SignInVerifyContract.UIState()
    )

    override fun onEventDispatcher(intent: SignInVerifyContract.Intent) = intent {
        when (intent) {
            SignInVerifyContract.Intent.SelectResend -> {
                if (networkStatusValidator.isNetworkEnabled){
                    resendUC.invoke()
                        .onStart { reduce { state.copy(showProgress = true) } }
                        .onSuccess { reduce { state.copy(resendCode = Random.nextFloat()) };println("success") }
                        .onFailure { postSideEffect(SignInVerifyContract.SideEffect.Message(it.message?:"Error happened"))}
                        .onCompletion { reduce { state.copy(showProgress = false) } }
                        .launchIn(viewModelScope)
                }else{
                    reduce { state.copy(networkError = true) }
                }

            }

            SignInVerifyContract.Intent.SelectBack -> {
                directions.moveBack()
            }

            is SignInVerifyContract.Intent.GoToPin -> {
                if (networkStatusValidator.isNetworkEnabled){
                    signInVerifyUC.invoke(
                        AuthData.Verify(
                            intent.code
                        )
                    ).onStart { }
                        .onSuccess {
                            directions.moveToPinScreen()
                        }
                        .onFailure {
                            postSideEffect(SignInVerifyContract.SideEffect.Message(it.message?:"Error happened"))
                        }
                        .onCompletion { }
                        .launchIn(viewModelScope)
                }else{
                    reduce { state.copy(networkError = true) }
                }

            }

        }
    }

}