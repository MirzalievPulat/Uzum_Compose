package uz.gita.uzumcompose.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.gita.uzumcompose.data.remote.request.AuthRequest
import uz.gita.uzumcompose.data.remote.response.AuthResponse

interface AuthApi {

    @POST("auth/sign-up")
    suspend fun signUp(@Body signUpRequest: AuthRequest.SignUp): Response<AuthResponse.SignUp>

    @POST("auth/sign-up/verify")
    suspend fun signUpVerify(@Body signUpVerifyRequest: AuthRequest.SignUpVerify):Response<AuthResponse.SignUpVerify>



    @POST("auth/sign-in")
    suspend fun signIn(@Body signInRequest: AuthRequest.SignIn): Response<AuthResponse.SignIn>

    @POST("auth/sign-in/verify")
    suspend fun signInVerify(@Body signInVerifyRequest: AuthRequest.SignInVerify): Response<AuthResponse.SignInVerify>



    @POST("auth/update-token")
    suspend fun updateToken(@Body updateTokenRequest: AuthRequest.UpdateToken): Response<AuthResponse.UpdateToken>



    @POST("auth/sign-up/resend")
    suspend fun signUpResend(@Body resendRequest: AuthRequest.Resend):Response<AuthResponse.Resend>

    @POST("auth/sign-in/resend")
    suspend fun signInResend(@Body resendRequest: AuthRequest.Resend):Response<AuthResponse.Resend>
}