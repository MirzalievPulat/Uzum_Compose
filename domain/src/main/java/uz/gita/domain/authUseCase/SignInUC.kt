package uz.gita.domain.authUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.AuthData

interface SignInUC {
    operator fun invoke(signIn: AuthData.SignIn): Flow<Result<Unit>>
}