package uz.gita.domain.useCase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.domain.model.request.AuthRequestModel
import uz.gita.domain.repository.AuthRepository
import uz.gita.domain.useCase.SignInUC

class SignInUCImpl constructor(private val repository: AuthRepository): SignInUC {
    override fun invoke(signInRequest: AuthRequestModel.SignIn) = repository.signIn(signInRequest)
}