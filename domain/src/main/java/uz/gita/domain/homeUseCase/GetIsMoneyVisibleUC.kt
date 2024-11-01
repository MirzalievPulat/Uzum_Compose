package uz.gita.domain.homeUseCase

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.HomeData

interface GetIsMoneyVisibleUC {
    operator fun invoke(): Flow<Result<Boolean>>
}