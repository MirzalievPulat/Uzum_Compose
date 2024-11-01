package uz.gita.entity.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.gita.entity.model.request.AuthRequest
import uz.gita.entity.model.response.AuthResponse

interface AuthApi {

    @POST("v1/auth/sign-up")
    suspend fun signUp(@Body signUpRequest: AuthRequest.SignUp): Response<AuthResponse.SignUp>

    @POST("v1/auth/sign-up/verify")
    suspend fun signUpVerify(@Body signUpVerifyRequest: AuthRequest.Verify): Response<AuthResponse.Verify>


    @POST("v1/auth/sign-in")
    suspend fun signIn(@Body signInRequest: AuthRequest.SignIn): Response<AuthResponse.SignIn>

    @POST("v1/auth/sign-in/verify")
    suspend fun signInVerify(@Body signInVerifyRequest: AuthRequest.Verify): Response<AuthResponse.Verify>


    @POST("v1/auth/update-token")
    suspend fun updateToken(@Body updateTokenRequest: AuthRequest.UpdateToken): Response<AuthResponse.UpdateToken>


    @POST("v1/auth/sign-up/resend")
    suspend fun signUpResend(@Body resendRequest: AuthRequest.Resend): Response<AuthResponse.Resend>

    @POST("v1/auth/sign-in/resend")
    suspend fun signInResend(@Body resendRequest: AuthRequest.Resend): Response<AuthResponse.Resend>
}