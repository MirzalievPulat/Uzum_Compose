package uz.gita.uzumcompose.domain.useCase

import kotlinx.coroutines.flow.Flow
import uz.gita.uzumcompose.domain.model.request.AuthRequestModel

interface SignInVerifyUC {
    operator fun invoke(signInVerify: AuthRequestModel.SignInVerify):Flow<Result<Unit>>
}