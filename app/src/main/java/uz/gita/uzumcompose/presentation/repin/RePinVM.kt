package uz.gita.uzumcompose.presentation.repin

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
import uz.gita.uzumcompose.presentation.pin.PinContract
import uz.gita.uzumcompose.presentation.pin.PinDirections
import uz.gita.uzumcompose.presentation.signInVerify.SignInVerifyContract
import uz.gita.uzumcompose.presentation.signInVerify.SignInVerifyDirections
import uz.gita.uzumcompose.presentation.signUpVerify.SignUpVerifyContract
import uz.gita.uzumcompose.presentation.signUpVerify.SignUpVerifyDirections
import javax.inject.Inject

@HiltViewModel
class RePinVM @Inject constructor(
    private val directions: PinDirections
) :ViewModel(), RePinContract.ViewModel{

    override val container =  container<RePinContract.UIState, RePinContract.SideEffect>(RePinContract
        .UIState())


    override fun onEventDispatcher(intent: RePinContract.Intent) = intent{
        when(intent){
            RePinContract.Intent.SelectBack->{
                directions.moveBack()
            }
        }
    }

}