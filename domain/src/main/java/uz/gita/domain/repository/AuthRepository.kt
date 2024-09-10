package uz.gita.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.domain.model.request.AfterSplash
import uz.gita.domain.model.request.AuthRequestModel

interface AuthRepository {
    fun signUp(signUpRequest: AuthRequestModel.SignUp): Flow<Result<Unit>>
    fun signIn(signInRequest: AuthRequestModel.SignIn): Flow<Result<Unit>>
    fun signUpVerify(signUpVerifyRequest: AuthRequestModel.SignUpVerify): Flow<Result<Unit>>
    fun signInVerify(signInVerifyRequest: AuthRequestModel.SignInVerify): Flow<Result<Unit>>
    fun signUpResend(): Flow<Result<Unit>>
    fun signInResend(): Flow<Result<Unit>>
    fun updateToken(): Flow<Result<Unit>>

    fun getNextScreen():AfterSplash
    fun logOut()
    fun setPin(pin:String)
}