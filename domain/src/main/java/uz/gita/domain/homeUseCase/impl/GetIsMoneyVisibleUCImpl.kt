package uz.gita.domain.homeUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.entity.repository.HomeRepository
import uz.gita.domain.homeUseCase.GetIsMoneyVisibleUC
import javax.inject.Inject

class GetIsMoneyVisibleUCImpl @Inject constructor(private val repository:HomeRepository): GetIsMoneyVisibleUC{
    override fun invoke(): Flow<Result<Boolean>> {
        return repository.getMoneyVisible()
    }
}