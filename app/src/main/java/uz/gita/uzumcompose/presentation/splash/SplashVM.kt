package uz.gita.uzumcompose.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.domain.model.request.AfterSplash
import uz.gita.domain.useCase.GetNextScreenUC
import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(
    private val direction: SplashContract.Direction,
    private val getNextScreen: GetNextScreenUC
) : ViewModel() {

    init {
        viewModelScope.launch {
            delay(500)
            when (getNextScreen()) {
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
    }

}

