package uz.gita.presentation.history

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.orbitmvi.orbit.ContainerHost
import uz.gita.common.data.HomeData

interface HistoryContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val isLoading:Boolean = false,
        val networkDialog:Boolean = false,
        val history:Flow<PagingData<HomeData.TransferInfo>> = flow {  }
    )

    sealed interface SideEffect {
        data class ToastMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun goBack()
    }

    interface Intent {
        object Back: Intent
        object NetworkDismiss:Intent
    }
}