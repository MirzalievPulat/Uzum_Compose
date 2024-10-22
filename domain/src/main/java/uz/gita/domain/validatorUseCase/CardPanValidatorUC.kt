package uz.gita.domain.validatorUseCase

interface CardPanValidatorUC {
    operator fun invoke(pan:String):String?
}