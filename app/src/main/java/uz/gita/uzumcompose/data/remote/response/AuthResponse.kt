package uz.gita.uzumcompose.data.remote.response


sealed class AuthResponse{
    data class SignUp(val token: String):AuthResponse()
    data class SignIn(val token: String):AuthResponse()
    data class SignUpVerify(val refreshToken: String,val accessToken: String):AuthResponse()
    data class SignInVerify(val refreshToken: String,val accessToken: String):AuthResponse()
    data class UpdateToken(val refreshToken: String,val accessToken: String):AuthResponse()
    data class Resend(val token: String):AuthResponse()
    data class Error(val message: String):AuthResponse()
}
