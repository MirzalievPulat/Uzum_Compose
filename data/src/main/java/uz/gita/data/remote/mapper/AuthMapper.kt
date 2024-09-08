package uz.gita.data.remote.mapper

import uz.gita.data.remote.request.AuthRequest
import uz.gita.domain.model.request.AfterSplash
import uz.gita.domain.model.request.AuthRequestModel

fun uz.gita.domain.model.request.AuthRequestModel.SignUp.toRequest() = AuthRequest.SignUp(phone,password,firstName,lastName,birthDate,gender)
fun uz.gita.domain.model.request.AuthRequestModel.SignIn.toRequest() = AuthRequest.SignIn(phone,password)
fun uz.gita.domain.model.request.AuthRequestModel.SignUpVerify.toRequest() = AuthRequest.SignUpVerify(token,code)
fun uz.gita.domain.model.request.AuthRequestModel.SignInVerify.toRequest() = AuthRequest.SignInVerify(token,code)
fun uz.gita.domain.model.request.AuthRequestModel.Resend.toRequest() = AuthRequest.Resend(token)
fun uz.gita.domain.model.request.AuthRequestModel.UpdateToken.toRequest() = AuthRequest.UpdateToken(refreshToken)

fun Int.toAfterSplash(): AfterSplash {
    return when(this){
        0-> AfterSplash.SIGN_UP
        1-> AfterSplash.SIGN_IN
        else -> {
            AfterSplash.PIN}
    }
}