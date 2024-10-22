package uz.gita.data.repository

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import uz.gita.common.data.AuthData
import uz.gita.common.other.AfterSplash
import uz.gita.data.extension.toResult
import uz.gita.data.locale.LocalStorage
import uz.gita.data.model.mapper.toRequest
import uz.gita.data.model.request.AuthRequest
import uz.gita.data.remote.api.AuthApi
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val localStorage: LocalStorage,
) : AuthRepository {
    val gson = Gson()
    override suspend fun signUp(signUp: AuthData.SignUp): Result<Unit> = withContext(Dispatchers.IO) {
        api.signUp(signUp.toRequest())
            .toResult {
                localStorage.token = it.token
                localStorage.name = signUp.firstName
            }
    }

    override fun signIn(signIn: AuthData.SignIn): Flow<Result<Unit>> = flow {
        val result = api.signIn(signIn.toRequest())
            .toResult {
                localStorage.token = it.token
            }
        emit(result)

    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun signUpVerify(verify: AuthData.Verify): Flow<Result<Unit>> = flow {
        val result = api.signUpVerify(AuthRequest.Verify(localStorage.token, verify.code))
            .toResult {
                localStorage.refreshToken = it.refreshToken
                localStorage.accessToken = it.accessToken
            }

        emit(result)
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun signInVerify(verify: AuthData.Verify): Flow<Result<Unit>> = flow {
        val result = api.signInVerify(AuthRequest.Verify(localStorage.token, verify.code))
            .toResult {
                localStorage.refreshToken = it.refreshToken
                localStorage.accessToken = it.accessToken
            }

        emit(result)
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun signUpResend(): Flow<Result<Unit>> = flow {
        val result = api.signUpResend(AuthRequest.Resend(localStorage.token)).toResult {
            localStorage.token = it.token
        }

        emit(result)
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun signInResend(): Flow<Result<Unit>> = flow {
        val result = api.signInResend(AuthRequest.Resend(localStorage.token)).toResult {
            localStorage.token = it.token
        }

        emit(result)
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun updateToken(): Flow<Result<Unit>> = flow {
        val result = api.updateToken(AuthRequest.UpdateToken(localStorage.refreshToken)).toResult {
            localStorage.refreshToken = it.refreshToken
            localStorage.accessToken = it.accessToken
        }

        emit(result)
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun getNextScreen(): Flow<Result<AfterSplash>> = flow {
        emit(Result.success(localStorage.afterSplash))
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun logOut(): Flow<Result<Unit>> = flow {
        localStorage.afterSplash = AfterSplash.SIGN_UP
        localStorage.pin = ""
        localStorage.token = ""
        localStorage.accessToken = ""
        localStorage.refreshToken = ""
        emit(Result.success(Unit))
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun setPin(pin: String): Flow<Result<Unit>> = flow {
        Log.d("TAG", "setPin: $pin")
        localStorage.pin = pin
        localStorage.afterSplash = AfterSplash.PIN
        Log.d("TAG", "setPin: ${localStorage.afterSplash.name} ")
        emit(Result.success(Unit))
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun getPin(): Flow<Result<String>> = flow {
        emit(Result.success(localStorage.pin))
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun getName(): Flow<Result<String>> = flow {
        emit(Result.success(localStorage.name))
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

}

