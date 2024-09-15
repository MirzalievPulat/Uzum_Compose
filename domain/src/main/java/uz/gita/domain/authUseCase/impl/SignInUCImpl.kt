package uz.gita.domain.authUseCase.impl

import uz.gita.common.data.AuthData
import uz.gita.data.model.request.AuthRequest
import uz.gita.data.repository.AuthRepository
import uz.gita.domain.authUseCase.SignInUC
import javax.inject.Inject

class SignInUCImpl @Inject constructor(private val repository: AuthRepository) : SignInUC {
    override fun invoke(signIn: AuthData.SignIn) = repository.signIn(signIn)
}