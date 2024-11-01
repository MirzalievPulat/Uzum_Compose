package uz.gita.presentation.auth.splash

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import uz.gita.common.other.AfterSplash
import uz.gita.domain.authUseCase.GetNextScreenUC
import uz.gita.domain.authUseCase.UpdateTokenUC
import uz.gita.presentation.helper.extensions.onFailure
import uz.gita.presentation.helper.extensions.onSuccess

import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(
    private val direction: SplashContract.Direction,
    private val getNextScreen: GetNextScreenUC,
    private val updateTokenUC: UpdateTokenUC,
    @ApplicationContext private val context: Context
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

        viewModelScope.launch {
            updateTokenUC()
                .onSuccess {  }
                .onFailure { Toast.makeText(context, "Splash Auth error: ${it.message}", Toast.LENGTH_SHORT).show() }
        }
    }
}

