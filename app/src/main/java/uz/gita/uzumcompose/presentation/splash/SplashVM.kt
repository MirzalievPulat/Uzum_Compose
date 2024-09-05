package uz.gita.uzumcompose.presentation.splash

import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.core.model.ScreenModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(
    private val direction: SplashContract.Direction
): ViewModel(), SplashContract.ViewModel {

    override val container =  container<SplashContract.UIState, SplashContract.SideEffect>(SplashContract.UIState.InitState)


    override fun onEventDispatcher(intent: SplashContract.Intent) = intent{
        when(intent){
            else -> {

            }
        }
    }

}

