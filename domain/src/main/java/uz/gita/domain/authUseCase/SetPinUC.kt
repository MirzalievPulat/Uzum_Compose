package uz.gita.domain.authUseCase

import kotlinx.coroutines.flow.Flow

interface SetPinUC {
    operator fun invoke(pin:String):Flow<Result<Unit>>
}