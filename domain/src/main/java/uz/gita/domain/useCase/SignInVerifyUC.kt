package uz.gita.domain.useCase

import kotlinx.coroutines.flow.Flow
import uz.gita.domain.model.request.AuthRequestModel

interface SignInVerifyUC {
    operator fun invoke(signInVerify: AuthRequestModel.SignInVerify):Flow<Result<Unit>>
}