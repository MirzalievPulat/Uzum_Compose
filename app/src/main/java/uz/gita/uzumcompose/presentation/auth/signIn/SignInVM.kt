package uz.gita.uzumcompose.presentation.auth.signIn

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
import uz.gita.domain.authUseCase.SignInUC
import uz.gita.uzumcompose.utils.extensions.onFailure
import uz.gita.uzumcompose.utils.extensions.onSuccess
import javax.inject.Inject

@HiltViewModel
class SignInVM @Inject constructor(
    private val signInUC: SignInUC,
    private val directions: SignInContract.Direction
) : ViewModel(), SignInContract.ViewModel {

    override val container = container<SignInContract.UIState, SignInContract.SideEffect>(SignInContract.UIState())


    override fun onEventDispatcher(intent: SignInContract.Intent) = intent {
        when (intent) {
            SignInContract.Intent.SelectSignUp -> {
                directions.moveToSignUp()
            }

            is SignInContract.Intent.ClickContinue -> {
                signInUC.invoke(
                    AuthData.SignIn(
                        phone = intent.phone,
                        password = intent.password,
                    )
                ).onStart { reduce { state.copy(isLoading = true) } }
                    .onSuccess { directions.moveToVerify(intent.phone) }
                    .onFailure { }
                    .onCompletion {
                        reduce { state.copy(isLoading = false) }
                    }
                    .launchIn(viewModelScope)
            }
        }
    }

}