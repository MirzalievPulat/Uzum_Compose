package uz.gita.domain.useCase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.domain.model.request.AfterSplash
import uz.gita.domain.repository.AuthRepository
import uz.gita.domain.useCase.GetNextScreenUC

class GetNextScreenUCImpl(private val authRepository: AuthRepository) : GetNextScreenUC {
    override fun invoke(): AfterSplash {
        return authRepository.getNextScreen()
    }
}