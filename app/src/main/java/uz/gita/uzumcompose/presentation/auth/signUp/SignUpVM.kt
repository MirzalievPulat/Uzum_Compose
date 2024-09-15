package uz.gita.uzumcompose.presentation.auth.signUp

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
import uz.gita.domain.authUseCase.SignUpUC
import uz.gita.uzumcompose.utils.NetworkStatusValidator
import uz.gita.uzumcompose.utils.extensions.onFailure
import uz.gita.uzumcompose.utils.extensions.onSuccess
import javax.inject.Inject

@HiltViewModel
class SignUpVM @Inject constructor(
    private val signUpUC: SignUpUC,
    private val directions: SignUpContract.Direction,
    private val networkStatusValidator: NetworkStatusValidator
) : ViewModel(), SignUpContract.ViewModel {

    override val container = container<SignUpContract.UIState, SignUpContract.SideEffect>(SignUpContract.UIState())


    override fun onEventDispatcher(intent: SignUpContract.Intent) = intent {
        when (intent) {
            SignUpContract.Intent.SelectSignIn -> {
                directions.moveToSignIn()
            }

            is SignUpContract.Intent.ClickContinue -> {
//                if (networkStatusValidator.isNetworkEnabled){
                    signUpUC.invoke(
                        AuthData.SignUp(
                            intent.phone,
                            intent.password,
                            intent.firstName,
                            intent.lastName,
                            intent.birthDate,
                            intent.genderType.toString()
                        )
                    ).onStart { reduce { state.copy(isLoading = true) } }
                        .onSuccess { directions.moveToVerify(intent.phone) }
                        .onFailure { Log.d("TAG", "onEventDispatcher: ${it.message.toString()}") }
                        .onCompletion {
                            reduce { state.copy(isLoading = false) }
                        }
                        .launchIn(viewModelScope)
//                }else{
//
//                }
            }
        }
    }
}