package uz.gita.uzumcompose.domain.useCase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.uzumcompose.domain.model.request.AuthRequestModel
import uz.gita.uzumcompose.domain.repository.AuthRepository
import uz.gita.uzumcompose.domain.useCase.SignInResendUC
import uz.gita.uzumcompose.domain.useCase.SignInUC

class SignInResendUCImpl constructor(private val repository: AuthRepository): SignInResendUC {
    override fun invoke(signInResendRequest: AuthRequestModel.Resend): Flow<Result<Unit>> = flow{
        emit(repository.signInResend(signInResendRequest))
    }
}