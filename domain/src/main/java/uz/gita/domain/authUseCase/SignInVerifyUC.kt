package uz.gita.domain.authUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.AuthData
import uz.gita.data.model.request.AuthRequest

interface SignInVerifyUC {
    operator fun invoke(verify: AuthData.Verify): Flow<Result<Unit>>
}