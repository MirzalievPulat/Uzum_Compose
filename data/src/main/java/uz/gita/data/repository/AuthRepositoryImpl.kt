package uz.gita.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.data.extension.toResult
import uz.gita.data.locale.LocalStorage
import uz.gita.data.remote.api.AuthApi
import uz.gita.data.remote.mapper.toAfterSplash
import uz.gita.data.remote.mapper.toRequest
import uz.gita.data.remote.request.AuthRequest
import uz.gita.domain.model.request.AfterSplash
import uz.gita.domain.model.request.AuthRequestModel
import uz.gita.domain.repository.AuthRepository
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(private val api: AuthApi, private val localStorage: LocalStorage) :
    AuthRepository {
    override fun signUp(signUpRequest: AuthRequestModel.SignUp): Flow<Result<Unit>> = flow {
        val result = api.signUp(signUpRequest.toRequest()).toResult {
            localStorage.token = it.token
        }
        emit(result)
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun signIn(signInRequest: AuthRequestModel.SignIn): Flow<Result<Unit>> = flow {
        val result = api.signIn(signInRequest.toRequest()).toResult {
            localStorage.token = it.token
        }
        emit(result)

    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun signUpVerify(signUpVerifyRequest: AuthRequestModel.SignUpVerify): Flow<Result<Unit>> = flow {
        val result = api.signUpVerify(AuthRequest.SignUpVerify(localStorage.token,signUpVerifyRequest.code)).toResult {
            localStorage.refreshToken = it.refreshToken
            localStorage.accessToken = it.accessToken

            localStorage.afterSplash = 3
        }

        emit(result)
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun signInVerify(signInVerifyRequest: AuthRequestModel.SignInVerify): Flow<Result<Unit>> = flow {
        val result = api.signInVerify(AuthRequest.SignInVerify(localStorage.token,signInVerifyRequest.code)).toResult {
            localStorage.refreshToken = it.refreshToken
            localStorage.accessToken = it.accessToken

            localStorage.afterSplash = 3
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

    override fun getNextScreen(): AfterSplash {
        return localStorage.afterSplash.toAfterSplash()
    }

    override fun logOut() {
        localStorage.afterSplash = 0
        localStorage.pin = ""
        localStorage.token = ""
        localStorage.accessToken = ""
        localStorage.refreshToken = ""
    }

    override fun setPin(pin: String) {
        localStorage.pin = pin
    }

}

