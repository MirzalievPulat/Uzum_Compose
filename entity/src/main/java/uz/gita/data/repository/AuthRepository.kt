package uz.gita.data.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.common.data.AuthData
import uz.gita.common.other.AfterSplash
import uz.gita.data.model.request.AuthRequest

interface AuthRepository {
    suspend fun signUp(signUp: AuthData.SignUp): Result<Unit>
    fun signIn(signIn: AuthData.SignIn): Flow<Result<Unit>>
    fun signUpVerify(verify: AuthData.Verify): Flow<Result<Unit>>
    fun signInVerify(verify: AuthData.Verify): Flow<Result<Unit>>
    fun signUpResend(): Flow<Result<Unit>>
    fun signInResend(): Flow<Result<Unit>>
    fun updateToken(): Flow<Result<Unit>>

    fun getNextScreen(): Flow<Result<AfterSplash>>
    fun logOut(): Flow<Result<Unit>>
    fun setPin(pin: String): Flow<Result<Unit>>
}