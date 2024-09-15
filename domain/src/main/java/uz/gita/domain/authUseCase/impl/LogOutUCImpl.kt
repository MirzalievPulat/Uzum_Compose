package uz.gita.domain.authUseCase.impl

import uz.gita.data.repository.AuthRepository
import uz.gita.domain.authUseCase.LogOutUC
import javax.inject.Inject

class LogOutUCImpl @Inject constructor(private val authRepository: AuthRepository) : LogOutUC {
    override fun invoke() = authRepository.logOut()
}