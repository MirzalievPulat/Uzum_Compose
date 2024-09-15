package uz.gita.domain.authUseCase.impl

import uz.gita.data.repository.AuthRepository
import uz.gita.domain.authUseCase.SignUpResendUC
import javax.inject.Inject

class SignUpResendUCImpl @Inject constructor(private val repository: AuthRepository) : SignUpResendUC {
    override fun invoke() = repository.signUpResend()
}