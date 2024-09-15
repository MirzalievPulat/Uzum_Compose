package uz.gita.domain.authUseCase.impl

import uz.gita.data.repository.AuthRepository
import uz.gita.domain.authUseCase.UpdateTokenUC
import javax.inject.Inject

class UpdateTokenUCImpl @Inject constructor(private val repository: AuthRepository) : UpdateTokenUC {
    override fun invoke() =
        repository.updateToken()
}