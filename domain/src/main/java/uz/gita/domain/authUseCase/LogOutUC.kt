package uz.gita.domain.authUseCase

import kotlinx.coroutines.flow.Flow

interface LogOutUC {
    operator fun invoke(): Flow<Result<Unit>>
}