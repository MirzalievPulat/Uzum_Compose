package uz.gita.presentation.pages.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MenuPageVM @Inject constructor(
    private val directions: MenuPageContract.Direction,
) : ViewModel(), MenuPageContract.ViewModel {
    init {
        Log.d("test", "MenuPageVm: ")
    }

    override val container = container<MenuPageContract.UIState, MenuPageContract.SideEffect>(MenuPageContract.UIState())


    override fun onEventDispatcher(intent: MenuPageContract.Intent) = intent {
        when (intent) {
            MenuPageContract.Intent.AddCardClick -> {
                directions.goAddCardScreen()
            }
        }
    }

}