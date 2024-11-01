package uz.gita.domain.authUseCase.impl

import uz.gita.entity.repository.AuthRepository
import uz.gita.domain.authUseCase.GetPinUC
import javax.inject.Inject

class GetPinUCImpl @Inject constructor(private val authRepository: AuthRepository) : GetPinUC {
    override fun invoke() = authRepository.getPin()
}