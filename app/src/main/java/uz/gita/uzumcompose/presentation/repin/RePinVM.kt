package uz.gita.uzumcompose.presentation.repin

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.domain.model.request.AuthRequestModel
import uz.gita.domain.useCase.SetPinUC
import uz.gita.domain.useCase.SignInVerifyUC
import uz.gita.domain.useCase.SignUpVerifyUC
import uz.gita.uzumcompose.presentation.pin.PinContract
import uz.gita.uzumcompose.presentation.pin.PinDirections
import uz.gita.uzumcompose.presentation.signInVerify.SignInVerifyContract
import uz.gita.uzumcompose.presentation.signInVerify.SignInVerifyDirections
import uz.gita.uzumcompose.presentation.signUpVerify.SignUpVerifyContract
import uz.gita.uzumcompose.presentation.signUpVerify.SignUpVerifyDirections
import uz.gita.uzumcompose.ui.theme.HintUzum
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class RePinVM @Inject constructor(
    private val directions: RePinDirections,
    private val setPinUC: SetPinUC
) :ViewModel(), RePinContract.ViewModel{

    override val container =  container<RePinContract.UIState, RePinContract.SideEffect>(RePinContract.UIState())


    override fun onEventDispatcher(intent: RePinContract.Intent) = intent{
        when(intent){
            RePinContract.Intent.SelectBack->{
                directions.moveBack()
            }
            is RePinContract.Intent.GoToMain->{
                if (intent.code1 == intent.code2){
                    reduce { state.copy(dotsColor = Color.Green) }
                    delay(200)
                    Log.d("TAG", "onEventDispatcher: code1 == code2")
                    setPinUC.invoke(intent.code2)
                    directions.moveToMainScreen()
                }else{
                    reduce { state.copy(errorAnim = Random.nextLong(), dotsColor = Color.Red) }
                    delay(500)
                    reduce { state.copy(errorAnim = 0, dotsColor = Color.HintUzum) }

                }
            }
        }
    }

}