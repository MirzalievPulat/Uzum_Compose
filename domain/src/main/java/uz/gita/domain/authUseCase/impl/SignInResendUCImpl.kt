package uz.gita.domain.authUseCase.impl

import uz.gita.data.repository.AuthRepository
import uz.gita.domain.authUseCase.SignInResendUC
import javax.inject.Inject

class SignInResendUCImpl @Inject constructor(private val repository: AuthRepository) : SignInResendUC {
    override fun invoke() = repository.signInResend()
}