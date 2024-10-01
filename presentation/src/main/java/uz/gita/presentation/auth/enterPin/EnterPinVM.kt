package uz.gita.presentation.auth.enterPin

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.domain.authUseCase.SetPinUC
import uz.gita.presentation.auth.repin.RePinContract
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class EnterPinVM @Inject constructor(
    private val directions: EnterPinContract.Direction,
    private val setPinUC: SetPinUC
) :ViewModel(), EnterPinContract.ViewModel{

    override val container =  container<EnterPinContract.UIState, EnterPinContract.SideEffect>(EnterPinContract.UIState())


    override fun onEventDispatcher(intent: EnterPinContract.Intent) = intent{
        when(intent){
            EnterPinContract.Intent.SelectBack->{
//                directions.moveBack()
            }
            is EnterPinContract.Intent.GoToMain->{
                if (intent.code1 == intent.code2){
                    reduce { state.copy(fourDigitCorrect = true) }
                    delay(200)
                    Log.d("TAG", "onEventDispatcher: code1 == code2")
                    setPinUC.invoke(intent.code2)
                    directions.moveToMainScreen()
                }else{
                    reduce { state.copy(errorAnim = Random.nextLong(), fourDigitCorrect = false) }
                    delay(500)
                    reduce { state.copy(errorAnim = 0, fourDigitCorrect = true) }

                }
            }
        }
    }

}