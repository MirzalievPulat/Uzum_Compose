package uz.gita.domain.authUseCase.impl

import android.util.Log
import uz.gita.data.repository.AuthRepository
import uz.gita.domain.authUseCase.SetPinUC
import javax.inject.Inject

class SetPinUCImpl @Inject constructor(private val authRepository: AuthRepository) : SetPinUC {
    override fun invoke(pin: String) = authRepository.setPin(pin).also { Log.d("TAG", "invoke: Pin:$pin") }
}