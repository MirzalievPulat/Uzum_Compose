package uz.gita.uzumcompose.domain.useCase

import kotlinx.coroutines.flow.Flow
import uz.gita.uzumcompose.data.remote.request.AuthRequest
import uz.gita.uzumcompose.domain.model.request.AuthRequestModel

interface SignInResendUC {
    operator fun invoke(signInResendRequest: AuthRequestModel.Resend): Flow<Result<Unit>>
}