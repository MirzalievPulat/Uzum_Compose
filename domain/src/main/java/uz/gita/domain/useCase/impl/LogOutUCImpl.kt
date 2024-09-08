package uz.gita.domain.useCase.impl

import uz.gita.domain.repository.AuthRepository
import uz.gita.domain.useCase.LogOutUC

class LogOutUCImpl(val authRepository: AuthRepository): LogOutUC {
    override fun invoke() {
        authRepository.logOut()
    }
}