package uz.gita.data.remote.mapper

import uz.gita.data.remote.request.AuthRequest
import uz.gita.domain.model.request.AfterSplash
import uz.gita.domain.model.request.AuthRequestModel

fun AuthRequestModel.SignUp.toRequest() = AuthRequest.SignUp(phone,password,firstName,lastName,birthDate,gender)
fun AuthRequestModel.SignIn.toRequest() = AuthRequest.SignIn(phone,password)



fun Int.toAfterSplash(): AfterSplash {
    return when(this){
        0-> AfterSplash.SIGN_UP
        1-> AfterSplash.SIGN_IN
        else -> {
            AfterSplash.PIN}
    }
}