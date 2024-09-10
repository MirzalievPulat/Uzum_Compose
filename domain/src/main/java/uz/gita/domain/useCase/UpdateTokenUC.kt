package uz.gita.domain.useCase

import kotlinx.coroutines.flow.Flow
import uz.gita.domain.model.request.AuthRequestModel

interface UpdateTokenUC {
    operator fun invoke(): Flow<Result<Unit>>
}