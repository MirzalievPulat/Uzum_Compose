package uz.gita.entity.model.request

import com.google.gson.annotations.SerializedName


sealed interface AuthRequest {
    data class SignUp(
        val phone: String,
        val password: String,
        @SerializedName("first-name")
        val firstName: String,
        @SerializedName("last-name")
        val lastName: String,
        @SerializedName("born-date")
        val birthDate: String,
        val gender: String
    )

    data class Verify(val token:String, val code: String)
    data class SignIn(val phone: String, val password: String)
    data class UpdateToken(@SerializedName("refresh-token") val refreshToken: String)
    data class Resend(val token: String)
}