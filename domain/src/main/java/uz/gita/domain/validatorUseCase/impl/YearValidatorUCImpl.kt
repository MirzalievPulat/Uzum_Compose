package uz.gita.domain.validatorUseCase.impl

import android.util.Log
import uz.gita.domain.validatorUseCase.CardPanValidatorUC
import uz.gita.domain.validatorUseCase.FirstNameValidatorUC
import uz.gita.domain.validatorUseCase.YearValidatorUC
import javax.inject.Inject

class YearValidatorUCImpl @Inject constructor(): YearValidatorUC {
    override fun invoke(year: String): String? {
//        Log.d("TAG", "invoke: year:$year")
//        if (year.length < 4 || year.substring(0,2).toInt() >= 13) return "Invalid number"
        if (year.length < 4 ) return "Invalid number"
//        Log.d("TAG", "invoke: year.substring(0,2).toInt():${year.substring(0,2).toInt()}")
        return null
    }
}