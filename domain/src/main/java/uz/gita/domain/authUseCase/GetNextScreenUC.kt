package uz.gita.domain.authUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.other.AfterSplash


interface GetNextScreenUC {
    operator fun invoke(): Flow<Result<AfterSplash>>
}