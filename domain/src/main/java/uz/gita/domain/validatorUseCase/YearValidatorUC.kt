package uz.gita.domain.validatorUseCase

interface YearValidatorUC {
    operator fun invoke(year:String):String?
}