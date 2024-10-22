package uz.gita.domain.validatorUseCase.impl

import uz.gita.domain.validatorUseCase.CardPanValidatorUC
import uz.gita.domain.validatorUseCase.FirstNameValidatorUC
import uz.gita.domain.validatorUseCase.YearValidatorUC
import javax.inject.Inject

class YearValidatorUCImpl @Inject constructor(): YearValidatorUC {
    override fun invoke(year: String): String? {
        if (year.length < 4 && year.substring(0,2).toInt() > 13) return "Invalid number"
        return null
    }
}