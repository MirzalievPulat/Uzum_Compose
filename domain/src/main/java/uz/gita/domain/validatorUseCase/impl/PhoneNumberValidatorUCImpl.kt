package uz.gita.domain.validatorUseCase.impl

import uz.gita.domain.validatorUseCase.PhoneNumberValidatorUC
import javax.inject.Inject

class PhoneNumberValidatorUCImpl @Inject constructor():PhoneNumberValidatorUC {
    val codes = listOf("99","90","91","93","94")

    override fun invoke(phoneNumber: String): String? {
        if (phoneNumber.length != 9) return "Invalid phone number"

        return if (codes.any { phoneNumber.startsWith(it) }) null else "Invalid phone number"
    }
}
