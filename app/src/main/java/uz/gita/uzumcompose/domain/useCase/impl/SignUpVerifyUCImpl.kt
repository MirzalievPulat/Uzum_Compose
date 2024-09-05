package uz.gita.uzumcompose.domain.useCase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.uzumcompose.domain.model.request.AuthRequestModel
import uz.gita.uzumcompose.domain.repository.AuthRepository
import uz.gita.uzumcompose.domain.useCase.SignUpVerifyUC

class SignUpVerifyUCImpl constructor(private val repository: AuthRepository):SignUpVerifyUC {
    override fun invoke(signUpVerify: AuthRequestModel.SignUpVerify): Flow<Result<Unit>> = flow{
        emit(repository.signUpVerify(signUpVerify))
    }
}