package uz.gita.domain.authUseCase

import kotlinx.coroutines.flow.Flow

interface SignUpResendUC {
    operator fun invoke(): Flow<Result<Unit>>
}