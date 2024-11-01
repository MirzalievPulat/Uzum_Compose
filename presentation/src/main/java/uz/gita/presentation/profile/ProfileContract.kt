package uz.gita.presentation.profile

import org.orbitmvi.orbit.ContainerHost

interface ProfileContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val isLoading:Boolean = false,
        val saveLoading:Boolean = false,
        val networkDialog:Boolean = false,
        val errorSurname:String? = null,
        val errorName:String? = null,
        val buttonEnabled:Boolean = false,

        val isMale:Boolean = false,
        val surname:String = "surname",
        val name:String = "name",
        val birthDate:String = "1190660400000"
    )

    sealed interface SideEffect {
        data class ToastMessage(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun goBack()
    }

    interface Intent {
        object SaveClick: Intent
        object Back: Intent
        object NetworkDialogDismiss: Intent
        data class NameChange(val name:String):Intent
        data class SurNameChange(val surName:String):Intent
        data class BirthDateChange(val date:Long?):Intent
        data class MaleClick(val isMale:Boolean):Intent
    }
}