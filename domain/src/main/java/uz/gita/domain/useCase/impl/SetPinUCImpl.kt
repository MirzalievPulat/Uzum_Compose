package uz.gita.domain.useCase.impl

import uz.gita.domain.repository.AuthRepository
import uz.gita.domain.useCase.SetPinUC

class SetPinUCImpl(val authRepository: AuthRepository):SetPinUC {
    override fun invoke(pin:String) {
        authRepository.setPin(pin)
    }
}