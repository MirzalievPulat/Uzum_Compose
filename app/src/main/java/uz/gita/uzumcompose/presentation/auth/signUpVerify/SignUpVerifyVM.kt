package uz.gita.uzumcompose.presentation.auth.signUpVerify

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.common.data.AuthData
import uz.gita.domain.authUseCase.SignUpResendUC
import uz.gita.domain.authUseCase.SignUpVerifyUC
import uz.gita.uzumcompose.utils.extensions.onFailure
import uz.gita.uzumcompose.utils.extensions.onSuccess
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class SignUpVerifyVM @Inject constructor(
    private val signUpVerifyUC: SignUpVerifyUC,
    private val resendUC: SignUpResendUC,
    private val directions: SignUpVerifyContract.Direction
) : ViewModel(), SignUpVerifyContract.ViewModel {

    override val container =
        container<SignUpVerifyContract.UIState, SignUpVerifyContract.SideEffect>(SignUpVerifyContract.UIState())

    override fun onEventDispatcher(intent: SignUpVerifyContract.Intent) = intent {
        when (intent) {
            SignUpVerifyContract.Intent.SelectResend -> {
                resendUC.invoke()
                    .onStart { reduce { state.copy(showProgress = true) } }
                    .onSuccess { reduce { state.copy(resendCode = Random.nextFloat()) } }
                    .onFailure { }
                    .onCompletion { reduce { state.copy(showProgress = false) } }
                    .launchIn(viewModelScope)
            }

            SignUpVerifyContract.Intent.SelectBack -> {
                directions.moveBack()
            }

            is SignUpVerifyContract.Intent.GoToPin -> {
                signUpVerifyUC.invoke(
                    AuthData.Verify(
                        intent.code
                    )
                ).onStart { }
                    .onSuccess {
                        Log.d("TAG", "onEventDispatcher: Success")
                        directions.moveToPinScreen()
                    }
                    .onFailure {
                        Log.d("TAG", "onEventDispatcher: ${it.message.toString()}")
                    }
                    .onCompletion { }
                    .launchIn(viewModelScope)
            }

        }
    }

}