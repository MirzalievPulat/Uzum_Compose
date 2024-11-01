package uz.gita.domain.authUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.AuthData

interface SignUpVerifyUC {
    operator fun invoke(verify: AuthData.Verify): Flow<Result<Unit>>
}