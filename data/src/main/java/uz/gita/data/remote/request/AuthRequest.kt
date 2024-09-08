package uz.gita.data.remote.request

import com.google.gson.annotations.SerializedName

sealed class AuthRequest {
    data class SignUp(
        val phone: String,
        val password: String,
        @SerializedName("`first-name`")
        val firstName: String,
        @SerializedName("`last-name`")
        val lastName: String,
        @SerializedName("`born-date`")
        val birthDate: String,
        val gender: String
    ): AuthRequest()

    data class SignUpVerify(val token: String, val code: String): AuthRequest()
    data class SignIn(val phone: String, val password: String): AuthRequest()
    data class SignInVerify(val token: String, val code: String): AuthRequest()
    data class UpdateToken(@SerializedName("`refresh-token`") val refreshToken: String): AuthRequest()
    data class Resend(val token: String): AuthRequest()
}