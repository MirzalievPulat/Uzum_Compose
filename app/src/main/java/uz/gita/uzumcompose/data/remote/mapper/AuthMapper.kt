package uz.gita.uzumcompose.data.remote.mapper

import uz.gita.uzumcompose.data.remote.request.AuthRequest
import uz.gita.uzumcompose.domain.model.request.AuthRequestModel

fun AuthRequestModel.SignUp.toRequest() = AuthRequest.SignUp(phone,password,firstName,lastName,birthDate,gender)
fun AuthRequestModel.SignIn.toRequest() = AuthRequest.SignIn(phone,password)
fun AuthRequestModel.SignUpVerify.toRequest() = AuthRequest.SignUpVerify(token,code)
fun AuthRequestModel.SignInVerify.toRequest() = AuthRequest.SignInVerify(token,code)
fun AuthRequestModel.Resend.toRequest() = AuthRequest.Resend(token)
fun AuthRequestModel.UpdateToken.toRequest() = AuthRequest.UpdateToken(refreshToken)