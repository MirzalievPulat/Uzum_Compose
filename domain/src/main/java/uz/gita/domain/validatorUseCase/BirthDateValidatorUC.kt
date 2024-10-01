package uz.gita.domain.validatorUseCase

interface BirthDateValidatorUC {
    operator fun invoke(birthDate:String):String?
}