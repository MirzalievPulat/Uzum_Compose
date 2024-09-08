package uz.gita.domain.useCase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.domain.model.request.AuthRequestModel
import uz.gita.domain.repository.AuthRepository
import uz.gita.domain.useCase.UpdateTokenUC

class UpdateTokenUCImpl constructor(private val repository: AuthRepository): UpdateTokenUC {
    override fun invoke(updateTokenRequest: AuthRequestModel.UpdateToken): Flow<Result<Unit>> = flow{
        emit(repository.updateToken(updateTokenRequest))
    }
}