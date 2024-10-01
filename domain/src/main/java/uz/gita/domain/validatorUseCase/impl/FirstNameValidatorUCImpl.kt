package uz.gita.domain.validatorUseCase.impl

import uz.gita.domain.validatorUseCase.FirstNameValidatorUC
import javax.inject.Inject

class FirstNameValidatorUCImpl @Inject constructor():FirstNameValidatorUC {
    override fun invoke(firstName: String): String? {
        if (firstName.length !in (2..26)) return "First name must be between 2 and 26 characters"
        return null
    }
}