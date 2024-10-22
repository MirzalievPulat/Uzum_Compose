package uz.gita.domain.validatorUseCase.impl

import uz.gita.domain.validatorUseCase.CardPanValidatorUC
import uz.gita.domain.validatorUseCase.FirstNameValidatorUC
import javax.inject.Inject

class CardPanValidatorUCImpl @Inject constructor(): CardPanValidatorUC {
    override fun invoke(pan: String): String? {
        if (pan.length < 16) return "Invalid card number"
        return null
    }
}