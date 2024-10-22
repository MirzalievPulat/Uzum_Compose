package uz.gita.domain.authUseCase

import kotlinx.coroutines.flow.Flow

interface GetPinUC {
    operator fun invoke():Flow<Result<String>>
}