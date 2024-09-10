package uz.gita.domain.useCase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.domain.model.request.AuthRequestModel
import uz.gita.domain.repository.AuthRepository
import uz.gita.domain.useCase.SignInResendUC
import uz.gita.domain.useCase.SignInUC

class SignInResendUCImpl constructor(private val repository: AuthRepository): SignInResendUC {
    override fun invoke() = repository.signInResend()
}