package uz.gita.domain.transferUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.domain.transferUseCase.GetTransferringCardDetails

import uz.gita.entity.repository.TransferRepository
import javax.inject.Inject

class GetTransferringCardDetailsUCImpl @Inject constructor(private val repository: TransferRepository): GetTransferringCardDetails {
    override fun invoke(): Flow<Result<Pair<String, String>>> {
        return repository.getTransferringCardDetails()
    }
}