package uz.gita.uzumcompose.domain.useCase

import kotlinx.coroutines.flow.Flow
import uz.gita.uzumcompose.data.remote.request.AuthRequest
import uz.gita.uzumcompose.domain.model.request.AuthRequestModel

interface SignInUC {
    operator fun invoke(signInRequest: AuthRequestModel.SignIn): Flow<Result<Unit>>
}