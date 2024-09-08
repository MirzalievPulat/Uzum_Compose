package uz.gita.domain.model.response

import com.google.gson.annotations.SerializedName


sealed class AuthResponseModel{
    data class SignOut(val token: String): AuthResponseModel()
    data class SignIn(val token: String): AuthResponseModel()
    data class SignUpVerify(val refreshToken: String,val accessToken: String): AuthResponseModel()
    data class SignInVerify(val refreshToken: String,val accessToken: String): AuthResponseModel()
    data class UpdateToken(val message: String): AuthResponseModel()
    data class Resend(val token: String): AuthResponseModel()
}