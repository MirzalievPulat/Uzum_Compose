package uz.gita.presentation.pages.transfer

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.presentation.pages.menu.MenuPageContract
import javax.inject.Inject

@HiltViewModel
class TransferPageVM @Inject constructor(
    private val directions: TransferPageContract.Direction,
) : ViewModel(), TransferPageContract.ViewModel {
    init {
        Log.d("test", "TransferPageVm: ")
    }

    override val container = container<TransferPageContract.UIState, TransferPageContract.SideEffect>(TransferPageContract.UIState())


    override fun onEventDispatcher(intent: TransferPageContract.Intent) = intent {
        when (intent) {
            TransferPageContract.Intent.PanTextFieldClick -> {
                directions.goTransferCardScreen()
            }
        }
    }

}