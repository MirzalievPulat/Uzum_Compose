package uz.gita.presentation.auth.signIn

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
import uz.gita.domain.authUseCase.SignInUC
import uz.gita.domain.validatorUseCase.PasswordValidatorUC
import uz.gita.domain.validatorUseCase.PhoneNumberValidatorUC
import uz.gita.common.other.NetworkStatusValidator
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess
import javax.inject.Inject

@HiltViewModel
class SignInVM @Inject constructor(
    private val signInUC: SignInUC,
    private val directions: SignInContract.Direction,
    private val phoneNumberValidatorUC: PhoneNumberValidatorUC,
    private val passwordValidatorUC: PasswordValidatorUC,
    private val networkStatusValidator: NetworkStatusValidator
) : ViewModel(), SignInContract.ViewModel {

    override val container = container<SignInContract.UIState, SignInContract.SideEffect>(SignInContract.UIState().copy
        (networkStatusValidator = networkStatusValidator))


    override fun onEventDispatcher(intent: SignInContract.Intent) = intent {
        when (intent) {
            SignInContract.Intent.SelectSignUp -> {
                directions.moveToSignUp()
            }

            SignInContract.Intent.DismissDialog -> {
                reduce { state.copy(networkDialog = false) }
            }

            is SignInContract.Intent.ClickContinue -> {
                Log.d("TAG", "onEventDispatcher: SignInContract.Intent.ClickContinue")
                if (!areInputsValid(intent)) return@intent
                Log.d("TAG", "onEventDispatcher: SignInContract.Intent.ClickContinue")

                if (networkStatusValidator.isNetworkEnabled){
                    Log.d("TAG", "onEventDispatcher: SignInContract.Intent.ClickContinue")
                    signInUC.invoke(
                        AuthData.SignIn(
                            phone = "+998"+intent.phone,
                            password = intent.password,
                        )
                    ).onStart { reduce { state.copy(isLoading = true) } }
                        .onSuccess { directions.moveToVerify("+998"+intent.phone) }
                        .onFailure { postSideEffect(SignInContract.SideEffect.Message(it.message?:"Error happened"))}
                        .onCompletion {
                            reduce { state.copy(isLoading = false) }
                        }
                        .launchIn(viewModelScope)
                }else{
                    Log.d("TAG", "onEventDispatcher: elese part")
                    reduce { state.copy(networkDialog = true) }
                }

            }
        }
    }

    private fun areInputsValid(intent: SignInContract.Intent.ClickContinue): Boolean {
        val phone = phoneNumberValidatorUC(intent.phone)
        val password = passwordValidatorUC(intent.password)

        intent {
            reduce {
                state.copy(
                    phoneNumberError = phone,
                    passwordError = password,
                )
            }
        }

        return phone == null  && password == null
    }

}