package uz.gita.uzumcompose.presentation.signInVerify

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
import uz.gita.domain.useCase.SignInVerifyUC
import uz.gita.domain.useCase.SignUpVerifyUC
import uz.gita.uzumcompose.presentation.signUpVerify.SignUpVerifyContract
import uz.gita.uzumcompose.presentation.signUpVerify.SignUpVerifyDirections
import javax.inject.Inject

@HiltViewModel
class SignInVerifyVM @Inject constructor(
    private val signInVerifyUC: uz.gita.domain.useCase.SignInVerifyUC,
    private val directions: SignInVerifyDirections
) :ViewModel(), SignInVerifyContract.ViewModel{

    override val container =  container<SignInVerifyContract.UIState, SignInVerifyContract.SideEffect>(SignInVerifyContract
        .UIState())


    override fun onEventDispatcher(intent: SignInVerifyContract.Intent) = intent{
        when(intent){
            SignInVerifyContract.Intent.SelectResend->{

            }
            SignInVerifyContract.Intent.SelectBack->{
                directions.moveBack()
            }
            SignInVerifyContract.Intent.SelectNotComing->{
            }

        }
    }

}