package uz.gita.domain.transferUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.domain.transferUseCase.SaveTransferringCardDetailsUC
import uz.gita.entity.repository.TransferRepository
import javax.inject.Inject

class SaveTransferringCardDetailsUCImpl @Inject constructor(private val repository: TransferRepository): SaveTransferringCardDetailsUC {
    override fun invoke(sum: String, pan: String): Flow<Result<Unit>> {
        return repository.saveTransferringCardDetails(sum, pan)
    }
}