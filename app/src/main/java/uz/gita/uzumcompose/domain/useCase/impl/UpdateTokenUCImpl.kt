package uz.gita.uzumcompose.domain.useCase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.uzumcompose.domain.model.request.AuthRequestModel
import uz.gita.uzumcompose.domain.repository.AuthRepository
import uz.gita.uzumcompose.domain.useCase.UpdateTokenUC

class UpdateTokenUCImpl constructor(private val repository: AuthRepository):UpdateTokenUC {
    override fun invoke(updateTokenRequest: AuthRequestModel.UpdateToken): Flow<Result<Unit>> = flow{
        emit(repository.updateToken(updateTokenRequest))
    }
}