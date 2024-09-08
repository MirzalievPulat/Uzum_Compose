package uz.gita.uzumcompose.presentation.signUpVerify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.domain.model.request.AuthRequestModel
import uz.gita.domain.useCase.SignUpResendUC
import uz.gita.domain.useCase.SignUpVerifyUC
import javax.inject.Inject

@HiltViewModel
class SignUpVerifyVM @Inject constructor(
    private val signUpVerifyUC: uz.gita.domain.useCase.SignUpVerifyUC,
    private val resendUC: uz.gita.domain.useCase.SignUpResendUC,
    private val directions: SignUpVerifyDirections
) :ViewModel(), SignUpVerifyContract.ViewModel{

    override val container =  container<SignUpVerifyContract.UIState, SignUpVerifyContract.SideEffect>(SignUpVerifyContract.UIState())


    override fun onEventDispatcher(intent: SignUpVerifyContract.Intent) = intent{
        when(intent){
            SignUpVerifyContract.Intent.SelectResend->{

            }
            SignUpVerifyContract.Intent.SelectBack->{
                directions.moveBack()
            }
            SignUpVerifyContract.Intent.SelectNotComing->{
            }

        }
    }

}