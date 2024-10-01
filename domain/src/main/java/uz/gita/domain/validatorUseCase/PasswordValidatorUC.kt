package uz.gita.domain.validatorUseCase

interface PasswordValidatorUC {
    operator fun invoke(password:String):String?
}