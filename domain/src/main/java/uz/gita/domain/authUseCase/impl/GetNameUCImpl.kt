package uz.gita.domain.authUseCase.impl

import uz.gita.data.repository.AuthRepository
import uz.gita.domain.authUseCase.GetNameUC
import uz.gita.domain.authUseCase.GetPinUC
import uz.gita.domain.authUseCase.SetPinUC
import javax.inject.Inject

class GetNameUCImpl @Inject constructor(private val authRepository: AuthRepository) : GetNameUC {
    override fun invoke() = authRepository.getName()
}