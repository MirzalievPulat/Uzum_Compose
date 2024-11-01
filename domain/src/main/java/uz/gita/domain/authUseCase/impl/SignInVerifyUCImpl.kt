package uz.gita.domain.authUseCase.impl

import uz.gita.common.data.AuthData
import uz.gita.entity.repository.AuthRepository
import uz.gita.domain.authUseCase.SignInVerifyUC
import javax.inject.Inject

class SignInVerifyUCImpl @Inject constructor(private val repository: AuthRepository) : SignInVerifyUC {
    override fun invoke(verify: AuthData.Verify) = repository.signInVerify(verify)
}