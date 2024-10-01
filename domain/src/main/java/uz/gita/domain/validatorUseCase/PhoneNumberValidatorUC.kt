package uz.gita.domain.validatorUseCase

interface PhoneNumberValidatorUC {
    operator fun invoke(phoneNumber:String):String?
}