package uz.gita.domain.authUseCase

import kotlinx.coroutines.flow.Flow

interface UpdateTokenUC {
    operator fun invoke(): Flow<Result<Unit>>
}