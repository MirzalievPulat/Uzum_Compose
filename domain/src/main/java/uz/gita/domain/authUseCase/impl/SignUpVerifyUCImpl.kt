package uz.gita.domain.authUseCase.impl

import uz.gita.common.data.AuthData
import uz.gita.entity.repository.AuthRepository
import uz.gita.domain.authUseCase.SignUpVerifyUC
import javax.inject.Inject

class SignUpVerifyUCImpl @Inject constructor(private val repository: AuthRepository) : SignUpVerifyUC {
    override fun invoke(verify: AuthData.Verify) =
        repository.signUpVerify(verify)
}