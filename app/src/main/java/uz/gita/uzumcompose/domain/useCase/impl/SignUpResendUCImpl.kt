package uz.gita.uzumcompose.domain.useCase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.uzumcompose.domain.model.request.AuthRequestModel
import uz.gita.uzumcompose.domain.repository.AuthRepository
import uz.gita.uzumcompose.domain.useCase.SignInResendUC
import uz.gita.uzumcompose.domain.useCase.SignInUC
import uz.gita.uzumcompose.domain.useCase.SignUpResendUC

class SignUpResendUCImpl constructor(private val repository: AuthRepository): SignUpResendUC {
    override fun invoke(signUpResendRequest: AuthRequestModel.Resend): Flow<Result<Unit>> = flow{
        emit(repository.signUpResend(signUpResendRequest))
    }
}