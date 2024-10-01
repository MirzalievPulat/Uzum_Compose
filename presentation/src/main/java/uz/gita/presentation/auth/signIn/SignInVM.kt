package uz.gita.presentation.auth.signIn

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
import uz.gita.presentation.auth.signUp.SignUpContract
import uz.gita.presentation.helper.NetworkStatusValidator
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

    override val container = container<SignInContract.UIState, SignInContract.SideEffect>(SignInContract.UIState())


    override fun onEventDispatcher(intent: SignInContract.Intent) = intent {
        when (intent) {
            SignInContract.Intent.SelectSignUp -> {
                directions.moveToSignUp()
            }

            is SignInContract.Intent.ClickContinue -> {
                if (!areInputsValid(intent)) return@intent

                if (networkStatusValidator.isNetworkEnabled){
                    signInUC.invoke(
                        AuthData.SignIn(
                            phone = intent.phone,
                            password = intent.password,
                        )
                    ).onStart { reduce { state.copy(isLoading = true) } }
                        .onSuccess { directions.moveToVerify(intent.phone) }
                        .onFailure { postSideEffect(SignInContract.SideEffect.Message(it.message?:"Error happened"))}
                        .onCompletion {
                            reduce { state.copy(isLoading = false) }
                        }
                        .launchIn(viewModelScope)
                }else{
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