package uz.gita.presentation.auth.signUp

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
import uz.gita.domain.authUseCase.SignUpUC
import uz.gita.domain.validatorUseCase.BirthDateValidatorUC
import uz.gita.domain.validatorUseCase.FirstNameValidatorUC
import uz.gita.domain.validatorUseCase.LastNameValidatorUC
import uz.gita.domain.validatorUseCase.PasswordValidatorUC
import uz.gita.domain.validatorUseCase.PhoneNumberValidatorUC
import uz.gita.common.other.NetworkStatusValidator
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess
import javax.inject.Inject

@HiltViewModel
class SignUpVM @Inject constructor(
    private val signUpUC: SignUpUC,
    private val directions: SignUpContract.Direction,
    private val firstNameValidatorUC: FirstNameValidatorUC,
    private val lastNameValidatorUC: LastNameValidatorUC,
    private val passwordValidatorUC: PasswordValidatorUC,
    private val phoneNumberValidatorUC: PhoneNumberValidatorUC,
    private val birthDateValidatorUC: BirthDateValidatorUC,
    private val networkStatusValidator: NetworkStatusValidator
) : ViewModel(), SignUpContract.ViewModel {

    override val container = container<SignUpContract.UIState, SignUpContract.SideEffect>(
        SignUpContract.UIState().copy
            (networkStatusValidator = networkStatusValidator)
    )


    override fun onEventDispatcher(intent: SignUpContract.Intent) = intent {
        when (intent) {
            SignUpContract.Intent.SelectSignIn -> {
                directions.moveToSignIn()
            }

            SignUpContract.Intent.DialogDismiss -> {
                reduce { state.copy(networkDialog = false) }
            }

            is SignUpContract.Intent.ClickContinue -> {
                if (!areInputsValid(intent)) return@intent

                if (networkStatusValidator.isNetworkEnabled) {
                    signUpUC.invoke(
                        AuthData.SignUp(
                            "+998" + intent.phone,
                            intent.password,
                            intent.firstName,
                            intent.lastName,
                            intent.birthDate,
                            intent.genderType.ordinal.toString()
                        )
                    ).onStart { reduce { state.copy(isLoading = true) } }
                        .onSuccess { directions.moveToVerify("+998" + intent.phone) }
                        .onFailure { postSideEffect(SignUpContract.SideEffect.Message(it.message ?: "Error happened")) }
                        .onCompletion {
                            reduce { state.copy(isLoading = false) }
                        }
                        .launchIn(viewModelScope)
                } else {
                    reduce { state.copy(networkDialog = true) }
                }

            }
        }
    }

    private fun areInputsValid(intent: SignUpContract.Intent.ClickContinue): Boolean {
        val phone = phoneNumberValidatorUC(intent.phone)
        val firstName = firstNameValidatorUC(intent.firstName)
        val lastName = lastNameValidatorUC(intent.lastName)
        val password = passwordValidatorUC(intent.password)
        val birthDate = birthDateValidatorUC(intent.birthDate)

        intent {
            reduce {
                state.copy(
                    phoneNumberError = phone,
                    firstNameError = firstName,
                    lastNameError = lastName,
                    passwordError = password,
                    birthDateError = birthDate
                )
            }
        }

        return phone == null && firstName == null && lastName == null && password == null && birthDate == null
    }
}
