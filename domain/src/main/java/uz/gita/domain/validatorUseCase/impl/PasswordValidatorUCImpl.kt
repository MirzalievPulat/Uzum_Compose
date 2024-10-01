package uz.gita.domain.validatorUseCase.impl

import uz.gita.domain.validatorUseCase.FirstNameValidatorUC
import uz.gita.domain.validatorUseCase.PasswordValidatorUC
import javax.inject.Inject

class PasswordValidatorUCImpl @Inject constructor(): PasswordValidatorUC {
    override fun invoke(password: String): String? {
        if (password.length !in (2..26)) return "Password must be between 2 and 26 characters"

        return null
    }
}