package uz.gita.domain.homeUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.HomeData

interface SetIsMoneyVisibleUC {
    operator fun invoke(isMoneyVisible:Boolean): Flow<Result<Unit>>
}