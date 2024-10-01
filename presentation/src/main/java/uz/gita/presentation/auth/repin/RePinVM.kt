package uz.gita.presentation.auth.repin

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.domain.authUseCase.SetPinUC
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class RePinVM @Inject constructor(
    private val directions: RePinContract.Direction,
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