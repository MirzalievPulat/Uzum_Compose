package uz.gita.domain.useCase.impl

import uz.gita.domain.repository.AuthRepository
import uz.gita.domain.useCase.UpdateTokenUC

class UpdateTokenUCImpl constructor(private val repository: AuthRepository): UpdateTokenUC {
    override fun invoke() =
       repository.updateToken()

}