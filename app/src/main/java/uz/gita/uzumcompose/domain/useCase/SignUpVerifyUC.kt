package uz.gita.uzumcompose.domain.useCase

import kotlinx.coroutines.flow.Flow
import uz.gita.uzumcompose.domain.model.request.AuthRequestModel

interface SignUpVerifyUC {
    operator fun invoke(signUpVerify: AuthRequestModel.SignUpVerify):Flow<Result<Unit>>
}