package uz.gita.uzumcompose.domain.useCase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.uzumcompose.domain.model.request.AuthRequestModel
import uz.gita.uzumcompose.domain.repository.AuthRepository
import uz.gita.uzumcompose.domain.useCase.SignUpUC

class SignUpUCImpl constructor(private val repository: AuthRepository):SignUpUC {
    override fun invoke(signUpRequest: AuthRequestModel.SignUp): Flow<Result<Unit>> = flow {
        emit(repository.signUp(signUpRequest))
    }
}