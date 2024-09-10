package uz.gita.domain.useCase

import kotlinx.coroutines.flow.Flow
import uz.gita.domain.model.request.AuthRequestModel

interface SignInResendUC {
    operator fun invoke(): Flow<Result<Unit>>
}