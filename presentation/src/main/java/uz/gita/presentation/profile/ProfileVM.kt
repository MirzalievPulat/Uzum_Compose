package uz.gita.presentation.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.common.data.HomeData
import uz.gita.domain.homeUseCase.GetFullInfoUC
import uz.gita.domain.homeUseCase.UpdateInfoUC
import uz.gita.domain.validatorUseCase.FirstNameValidatorUC
import uz.gita.domain.validatorUseCase.LastNameValidatorUC
import uz.gita.common.other.NetworkStatusValidator
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(
    private val getFullInfoUC: GetFullInfoUC,
    private val updateInfoUC: UpdateInfoUC,
    private val directions: ProfileContract.Direction,
    private val lastNameValidatorUC: LastNameValidatorUC,
    private val firstNameValidatorUC: FirstNameValidatorUC,
    private val networkStatusValidator: NetworkStatusValidator
) : ViewModel(), ProfileContract.ViewModel {
    private lateinit var initInfo:HomeData.FullInfo

    override val container = container<ProfileContract.UIState, ProfileContract.SideEffect>(ProfileContract.UIState())

    init {
        getFullInfoUC()
            .onStart { intent { reduce { state.copy(isLoading = true) } } }
            .onSuccess {
                initInfo = it
                intent {
                    reduce {
                        state.copy(
                            name = it.firstName, surname = it.lastName, birthDate = it.birthDate,
                            isMale = it.gender == "0"
                        )
                    }
                }
            }
            .onFailure {
                intent { postSideEffect(ProfileContract.SideEffect.ToastMessage(it.message ?: "Unknown error")) }
            }
            .onCompletion { intent { reduce { state.copy(isLoading = false) } } }
            .launchIn(viewModelScope)
    }

    override fun onEventDispatcher(intent: ProfileContract.Intent) = intent {
        when (intent) {
            is ProfileContract.Intent.SaveClick -> {
                if (networkStatusValidator.isNetworkEnabled) {
                    if (!areInputsValid(state)) return@intent
                    updateInfoUC.invoke(
                        HomeData.UpdateInfo(
                            bornDate = state.birthDate,
                            lastName = state.surname,
                            firstName = state.name,
                            genderType = if (state.isMale) "0" else "1"
                        )
                    )
                        .onStart { reduce { state.copy(saveLoading = true) } }
                        .onSuccess {
                            directions.goBack()
                        }
                        .onFailure {
                            postSideEffect(ProfileContract.SideEffect.ToastMessage(it.message ?: "Unknown error"))
                            reduce { state.copy(isLoading = false) }
                        }
                        .onCompletion { reduce { state.copy(isLoading = false) } }
                        .launchIn(viewModelScope)
                } else {
                    reduce { state.copy(networkDialog = true) }
                }
            }

            is ProfileContract.Intent.NameChange -> {
                val name = intent.name
                if (name.length < 20 && !name.contains(" ")){
                    reduce { state.copy(name = name) }
                    changeHappened()
                }

            }

            is ProfileContract.Intent.SurNameChange -> {
                val surName = intent.surName
                if (surName.length < 20 && !surName.contains(" ")){
                    reduce { state.copy(surname = surName) }
                    changeHappened()
                }
            }

            is ProfileContract.Intent.BirthDateChange -> {
                if (intent.date != null){
                    reduce { state.copy(birthDate = intent.date.toString()) }
                    changeHappened()
                }
            }

            is ProfileContract.Intent.MaleClick -> {
                reduce { state.copy(isMale = intent.isMale) }
                changeHappened()
            }

            ProfileContract.Intent.Back -> {
                directions.goBack()
            }

            ProfileContract.Intent.NetworkDialogDismiss -> {
                reduce { state.copy(networkDialog = false) }
            }
        }
    }

    private fun changeHappened() = intent {
        val gender = if(state.isMale) "0" else "1"
        if (initInfo.gender != gender || initInfo.birthDate != state.birthDate ||
            initInfo.lastName != state.surname || initInfo.firstName != state.name){
            Log.d("TAG", "changeHappened: ${initInfo.gender != gender} ${initInfo.birthDate != state.birthDate} " +
                    "${initInfo.lastName != state.surname} ${initInfo.firstName != state.name}")
            Log.d("TAG", "changeHappened: initBirth:${initInfo.birthDate} afterBirthDate:${state.birthDate}")
            reduce{ state.copy(buttonEnabled = true) }
        }else reduce { state.copy(buttonEnabled = false)}
    }

    private fun areInputsValid(state: ProfileContract.UIState): Boolean {
        val surname = lastNameValidatorUC(state.surname)
        val name = firstNameValidatorUC(state.name)

        intent {
            reduce {
                state.copy(
                    errorSurname = surname,
                    errorName = name
                )
            }
        }

        return surname == null && name == null
    }
}