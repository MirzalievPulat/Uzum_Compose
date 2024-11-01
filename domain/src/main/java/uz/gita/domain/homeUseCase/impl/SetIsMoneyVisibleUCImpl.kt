package uz.gita.domain.homeUseCase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.entity.repository.HomeRepository
import uz.gita.domain.homeUseCase.SetIsMoneyVisibleUC
import javax.inject.Inject

class SetIsMoneyVisibleUCImpl @Inject constructor(private val repository:HomeRepository): SetIsMoneyVisibleUC {
    override fun invoke(isMoneyVisible:Boolean): Flow<Result<Unit>> {
        return repository.setMoneyVisible(isMoneyVisible)
    }
}