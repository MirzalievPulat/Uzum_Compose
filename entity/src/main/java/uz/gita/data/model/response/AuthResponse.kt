package uz.gita.data.model.response

import com.google.gson.annotations.SerializedName


sealed class AuthResponse {
    data class SignUp(val token: String)
    data class SignIn(val token: String)
    data class Verify(
        @SerializedName("refresh-token") val refreshToken: String,
        @SerializedName("access-token") val accessToken: String
    )

    data class UpdateToken(
        @SerializedName("refresh-token") val refreshToken: String,
        @SerializedName("access-token") val accessToken: String
    )

    data class Resend(val token: String)
//    data class Error(val message: String)
}
