package uz.gita.domain.useCase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.domain.model.request.AuthRequestModel
import uz.gita.domain.repository.AuthRepository
import uz.gita.domain.useCase.SignUpVerifyUC

class SignUpVerifyUCImpl constructor(private val repository: AuthRepository): SignUpVerifyUC {
    override fun invoke(signUpVerify: AuthRequestModel.SignUpVerify) =
       repository.signUpVerify(signUpVerify)

}