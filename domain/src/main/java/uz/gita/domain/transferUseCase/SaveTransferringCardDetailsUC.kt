package uz.gita.domain.transferUseCase

import kotlinx.coroutines.flow.Flow

interface SaveTransferringCardDetailsUC {
    operator fun invoke(sum:String,pan:String): Flow<Result<Unit>>
}