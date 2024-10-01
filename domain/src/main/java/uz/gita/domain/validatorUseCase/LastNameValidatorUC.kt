package uz.gita.domain.validatorUseCase

interface LastNameValidatorUC {
    operator fun invoke(lastName:String):String?
}