package uz.gita.data.remote.response

import com.google.gson.annotations.SerializedName


sealed class AuthResponse{
    data class SignUp(val token: String): AuthResponse()
    data class SignIn(val token: String): AuthResponse()
    data class SignUpVerify(
        @SerializedName("refresh-token") val refreshToken: String,
        @SerializedName("access-token")val accessToken: String): AuthResponse()
    data class SignInVerify(
        @SerializedName("refresh-token") val refreshToken: String,
        @SerializedName("access-token")val accessToken: String): AuthResponse()
    data class UpdateToken(
        @SerializedName("refresh-token") val refreshToken: String,
        @SerializedName("access-token")val accessToken: String): AuthResponse()
    data class Resend(val token: String): AuthResponse()
    data class Error(val message: String): AuthResponse()
}
