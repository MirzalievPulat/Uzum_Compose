package uz.gita.domain.useCase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.domain.model.request.AuthRequestModel
import uz.gita.domain.repository.AuthRepository
import uz.gita.domain.useCase.SignInVerifyUC
import uz.gita.domain.useCase.SignUpVerifyUC

class SignInVerifyUCImpl constructor(private val repository: AuthRepository): SignInVerifyUC {
    override fun invoke(signInVerify: AuthRequestModel.SignInVerify): Flow<Result<Unit>> = flow{
        emit(repository.signInVerify(signInVerify))
    }
}