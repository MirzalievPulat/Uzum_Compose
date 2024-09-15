package uz.gita.domain.authUseCase.impl

import uz.gita.data.repository.AuthRepository
import uz.gita.domain.authUseCase.SetPinUC
import javax.inject.Inject

class SetPinUCImpl @Inject constructor(private val authRepository: AuthRepository) : SetPinUC {
    override fun invoke(pin: String) = authRepository.setPin(pin)
}