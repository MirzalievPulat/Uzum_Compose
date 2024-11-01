package uz.gita.domain.authUseCase.impl

import uz.gita.entity.repository.AuthRepository
import uz.gita.domain.authUseCase.GetNextScreenUC
import javax.inject.Inject

class GetNextScreenUCImpl @Inject constructor(private val authRepository: AuthRepository) : GetNextScreenUC {
    override fun invoke() = authRepository.getNextScreen()
}