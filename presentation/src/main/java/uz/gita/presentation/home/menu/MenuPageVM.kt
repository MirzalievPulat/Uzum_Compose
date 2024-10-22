package uz.gita.presentation.home.menu

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
import uz.gita.domain.cardUseCase.GetCardsUC
import uz.gita.domain.homeUseCase.GetTotalBalanceUC
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess
import uz.gita.presentation.home.home.HomePageContract
import javax.inject.Inject

@HiltViewModel
class MenuPageVM @Inject constructor(
    private val directions: MenuPageContract.Direction,
) : ViewModel(), MenuPageContract.ViewModel {

    override val container = container<MenuPageContract.UIState, MenuPageContract.SideEffect>(MenuPageContract.UIState())


    override fun onEventDispatcher(intent: MenuPageContract.Intent) = intent {
        when (intent) {
            MenuPageContract.Intent.AddCardClick -> {
                directions.goAddCardScreen()
            }
        }
    }

}