package uz.gita.domain.useCase

import kotlinx.coroutines.flow.Flow
import uz.gita.domain.model.request.AuthRequestModel

interface SignUpResendUC {
    operator fun invoke(signUpResendRequest: AuthRequestModel.Resend): Flow<Result<Unit>>
}