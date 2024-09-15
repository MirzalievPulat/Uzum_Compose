package uz.gita.common.data


sealed interface AuthData {
    data class SignUp(
        val phone: String,
        val password: String,
        val firstName: String,
        val lastName: String,
        val birthDate: String,
        val gender: String
    ):AuthData

    data class SignIn(val phone: String, val password: String): AuthData

    data class Verify(val code:String):AuthData
}