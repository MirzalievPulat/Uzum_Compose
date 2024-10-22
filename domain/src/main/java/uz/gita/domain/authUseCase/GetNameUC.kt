package uz.gita.domain.authUseCase

import kotlinx.coroutines.flow.Flow

interface GetNameUC {
    operator fun invoke():Flow<Result<String>>
}