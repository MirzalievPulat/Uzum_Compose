package uz.gita.uzumcompose.domain.repository

import uz.gita.uzumcompose.domain.model.request.AuthRequestModel

interface AuthRepository {
    suspend fun signUp(signUpRequest: AuthRequestModel.SignUp): Result<Unit>
    suspend fun signIn(signInRequest: AuthRequestModel.SignIn): Result<Unit>
    suspend fun signUpVerify(signUpVerifyRequest: AuthRequestModel.SignUpVerify): Result<Unit>
    suspend fun signInVerify(signInVerifyRequest: AuthRequestModel.SignInVerify): Result<Unit>
    suspend fun signUpResend(signUpResendRequest: AuthRequestModel.Resend): Result<Unit>
    suspend fun signInResend(signInResendRequest: AuthRequestModel.Resend): Result<Unit>
    suspend fun updateToken(updateTokenRequest: AuthRequestModel.UpdateToken): Result<Unit>
}