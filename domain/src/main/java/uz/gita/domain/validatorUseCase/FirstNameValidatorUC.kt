package uz.gita.domain.validatorUseCase

interface FirstNameValidatorUC {
    operator fun invoke(firstName:String):String?
}