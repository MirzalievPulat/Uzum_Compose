package uz.gita.domain.authUseCase.impl

import uz.gita.entity.repository.AuthRepository
import uz.gita.domain.authUseCase.GetNameUC
import javax.inject.Inject

class GetNameUCImpl @Inject constructor(private val authRepository: AuthRepository) : GetNameUC {
    override fun invoke() = authRepository.getName()
}