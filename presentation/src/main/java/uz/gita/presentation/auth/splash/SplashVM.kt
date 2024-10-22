package uz.gita.presentation.auth.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import uz.gita.common.other.AfterSplash
import uz.gita.domain.authUseCase.GetNextScreenUC
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess

import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(
    private val direction: SplashContract.Direction,
    private val getNextScreen: GetNextScreenUC
) : ViewModel() {

    init {
        viewModelScope.launch {
            delay(500)
            getNextScreen.invoke()
                .onSuccess {
                    Log.d("TAG", "after splash: ${it.name}")
                    when (it) {
                        AfterSplash.SIGN_UP -> {
                            direction.moveToSignUp()
                        }

                        AfterSplash.SIGN_IN -> {
                            direction.moveToSignIn()
                        }

                        AfterSplash.PIN -> {
                            direction.moveToPin()
                        }
                    }
                }
                .onFailure { }
                .launchIn(viewModelScope)
        }
    }
}

