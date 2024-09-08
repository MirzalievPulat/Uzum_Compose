package uz.gita.domain.useCase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.domain.model.request.AuthRequestModel
import uz.gita.domain.repository.AuthRepository
import uz.gita.domain.useCase.SignUpUC

class SignUpUCImpl constructor(private val repository: AuthRepository): SignUpUC {
    override fun invoke(signUpRequest: AuthRequestModel.SignUp): Flow<Result<Unit>> = flow {
        emit(repository.signUp(signUpRequest))
    }
}