package uz.gita.domain.validatorUseCase.impl

import uz.gita.domain.validatorUseCase.BirthDateValidatorUC
import javax.inject.Inject

class BirthDateValidatorUCImpl @Inject constructor():BirthDateValidatorUC {
    override fun invoke(birthDate: String): String? {
        if (birthDate == "0") return "Invalid birth date"
        return null
    }
}