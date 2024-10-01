package uz.gita.domain.validatorUseCase.impl

import uz.gita.domain.validatorUseCase.FirstNameValidatorUC
import uz.gita.domain.validatorUseCase.LastNameValidatorUC
import javax.inject.Inject

class LastNameValidatorUCImpl @Inject constructor(): LastNameValidatorUC {
    override fun invoke(lastName: String): String? {
        if (lastName.length !in (2..26)) return "Last name must be between 2 and 26 characters"
        return null
    }
}