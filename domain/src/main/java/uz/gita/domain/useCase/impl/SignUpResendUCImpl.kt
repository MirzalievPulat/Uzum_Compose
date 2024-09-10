package uz.gita.domain.useCase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.domain.model.request.AuthRequestModel
import uz.gita.domain.repository.AuthRepository
import uz.gita.domain.useCase.SignInResendUC
import uz.gita.domain.useCase.SignInUC
import uz.gita.domain.useCase.SignUpResendUC

class SignUpResendUCImpl constructor(private val repository: AuthRepository): SignUpResendUC {
    override fun invoke() = repository.signUpResend()
}