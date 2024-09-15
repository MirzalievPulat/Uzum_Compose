package uz.gita.domain.authUseCase

import kotlinx.coroutines.flow.Flow

interface SignInResendUC {
    operator fun invoke(): Flow<Result<Unit>>
}