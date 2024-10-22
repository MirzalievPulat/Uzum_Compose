package uz.gita.domain.authUseCase.impl

import uz.gita.data.repository.AuthRepository
import uz.gita.domain.authUseCase.GetPinUC
import uz.gita.domain.authUseCase.SetPinUC
import javax.inject.Inject

class GetPinUCImpl @Inject constructor(private val authRepository: AuthRepository) : GetPinUC {
    override fun invoke() = authRepository.getPin()
}