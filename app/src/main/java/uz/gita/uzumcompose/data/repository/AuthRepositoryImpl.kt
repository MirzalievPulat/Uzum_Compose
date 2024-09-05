package uz.gita.uzumcompose.data.repository

import uz.gita.uzumcompose.data.locale.LocalStorage
import uz.gita.uzumcompose.data.remote.api.AuthApi
import uz.gita.uzumcompose.data.remote.mapper.toRequest
import uz.gita.uzumcompose.domain.model.request.AuthRequestModel
import uz.gita.uzumcompose.domain.repository.AuthRepository

class AuthRepositoryImpl constructor(private val api:AuthApi, private val localStorage: LocalStorage):AuthRepository {
    override suspend fun signUp(signUpRequest: AuthRequestModel.SignUp): Result<Unit> {
        val response = api.signUp(signUpRequest.toRequest())

        return if (response.isSuccessful && response.body() != null){
            localStorage.token = response.body()!!.token
            Result.success(Unit)
        }else{
            Result.failure(Exception(response.message()))
        }
    }

    override suspend fun signIn(signInRequest: AuthRequestModel.SignIn): Result<Unit> {
        val response = api.signIn(signInRequest.toRequest())

        return if (response.isSuccessful && response.body() != null){
            localStorage.token = response.body()!!.token
            Result.success(Unit)
        }else{
            Result.failure(Exception(response.message()))
        }
    }

    override suspend fun signUpVerify(signUpVerifyRequest: AuthRequestModel.SignUpVerify): Result<Unit> {
        val response = api.signUpVerify(signUpVerifyRequest.toRequest())

        return if (response.isSuccessful && response.body() != null){
            localStorage.refreshToken = response.body()!!.refreshToken
            localStorage.accessToken = response.body()!!.accessToken
            Result.success(Unit)
        }else{
            Result.failure(Exception(response.message()))
        }
    }

    override suspend fun signInVerify(signInVerifyRequest: AuthRequestModel.SignInVerify): Result<Unit> {
        val response = api.signInVerify(signInVerifyRequest.toRequest())

        return if (response.isSuccessful && response.body() != null){
            localStorage.refreshToken = response.body()!!.refreshToken
            localStorage.accessToken = response.body()!!.accessToken
            Result.success(Unit)
        }else{
            Result.failure(Exception(response.message()))
        }
    }

    override suspend fun signUpResend(signUpResendRequest: AuthRequestModel.Resend): Result<Unit> {
        val response = api.signUpResend(signUpResendRequest.toRequest())

        return if (response.isSuccessful && response.body() != null){
            localStorage.token = response.body()!!.token
            Result.success(Unit)
        }else{
            Result.failure(Exception(response.message()))
        }
    }

    override suspend fun signInResend(signInResendRequest: AuthRequestModel.Resend): Result<Unit> {
        val response = api.signInResend(signInResendRequest.toRequest())

        return if (response.isSuccessful && response.body() != null){
            localStorage.token = response.body()!!.token
            Result.success(Unit)
        }else{
            Result.failure(Exception(response.message()))
        }
    }

    override suspend fun updateToken(updateTokenRequest: AuthRequestModel.UpdateToken): Result<Unit> {
        val response = api.updateToken(updateTokenRequest.toRequest())

        return if (response.isSuccessful && response.body() != null){
            localStorage.refreshToken = response.body()!!.refreshToken
            localStorage.accessToken = response.body()!!.accessToken
            Result.success(Unit)
        }else{
            Result.failure(Exception(response.message()))
        }
    }
}