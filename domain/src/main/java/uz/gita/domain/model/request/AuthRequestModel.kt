package uz.gita.domain.model.request

import com.google.gson.annotations.SerializedName


sealed class AuthRequestModel{
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
    ): AuthRequestModel()
    data class SignUpVerify(val code: String): AuthRequestModel()
    data class SignIn(val phone: String, val password: String): AuthRequestModel()
    data class SignInVerify(val code: String): AuthRequestModel()
//    data class UpdateToken(): AuthRequestModel()
//    data class Resend(val token: String): AuthRequestModel()
}

enum class AfterSplash(val screen:Int){
    SIGN_UP(0),SIGN_IN(1),PIN(2)
}