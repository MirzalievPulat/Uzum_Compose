package uz.gita.uzumcompose.domain.useCase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.uzumcompose.domain.model.request.AuthRequestModel
import uz.gita.uzumcompose.domain.repository.AuthRepository
import uz.gita.uzumcompose.domain.useCase.SignInUC

class SignInUCImpl constructor(private val repository: AuthRepository):SignInUC {
    override fun invoke(signInRequest: AuthRequestModel.SignIn): Flow<Result<Unit>> = flow{
        emit(repository.signIn(signInRequest))
    }
}