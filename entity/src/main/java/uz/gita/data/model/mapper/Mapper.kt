package uz.gita.data.model.mapper

import uz.gita.common.data.AuthData
import uz.gita.data.model.request.AuthRequest


fun AuthData.SignUp.toRequest(): AuthRequest.SignUp =
    AuthRequest.SignUp(phone, password, firstName, lastName, birthDate, gender)

fun AuthData.SignIn.toRequest(): AuthRequest.SignIn = AuthRequest.SignIn(phone, password)
