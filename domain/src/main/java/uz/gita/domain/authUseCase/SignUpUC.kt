package uz.gita.domain.authUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.AuthData
import uz.gita.data.model.request.AuthRequest

interface SignUpUC {
    operator fun invoke(signUp: AuthData.SignUp): Flow<Result<Unit>>
}