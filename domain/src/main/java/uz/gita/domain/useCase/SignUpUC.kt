package uz.gita.domain.useCase

import kotlinx.coroutines.flow.Flow
import uz.gita.domain.model.request.AuthRequestModel

interface SignUpUC {
    operator fun invoke(signUpRequest: AuthRequestModel.SignUp): Flow<Result<Unit>>
}