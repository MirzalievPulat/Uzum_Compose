package uz.gita.uzumcompose.presentation.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.domain.model.request.AuthRequestModel
import uz.gita.domain.useCase.SignUpUC
import javax.inject.Inject

@HiltViewModel
class SignUpVM @Inject constructor(
    private val signUpUC: SignUpUC,
    private val directions: SignUpDirections
) :ViewModel(),SignUpContract.ViewModel{

    override val container =  container<SignUpContract.UIState, SignUpContract.SideEffect>(SignUpContract.UIState())


    override fun onEventDispatcher(intent: SignUpContract.Intent) = intent{
        when(intent){
            SignUpContract.Intent.SelectSignIn->{
                directions.moveToSignIn()
            }
            is SignUpContract.Intent.ClickContinue->{
                signUpUC.invoke(
                    AuthRequestModel.SignUp(
                        intent.phone,
                        intent.password,
                        intent.firstName,
                        intent.lastName,
                        intent.birthDate,
                        intent.genderType.toString()
                    )
                ).onStart { reduce { state.copy(isLoading = true) } }
                    .onCompletion {
                        reduce { state.copy(isLoading = false) }
                        directions.moveToVerify()
                    }
                    .launchIn(viewModelScope)
            }
        }
    }

}