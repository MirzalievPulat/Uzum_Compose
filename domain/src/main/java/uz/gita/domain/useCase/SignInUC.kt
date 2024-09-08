package uz.gita.domain.useCase

import kotlinx.coroutines.flow.Flow
import uz.gita.domain.model.request.AuthRequestModel

interface SignInUC {
    operator fun invoke(signInRequest: AuthRequestModel.SignIn): Flow<Result<Unit>>
}