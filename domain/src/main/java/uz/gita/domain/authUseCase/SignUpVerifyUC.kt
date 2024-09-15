package uz.gita.domain.authUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.AuthData
import uz.gita.data.model.request.AuthRequest

interface SignUpVerifyUC {
    operator fun invoke(verify: AuthData.Verify): Flow<Result<Unit>>
}