package uz.gita.data.model.mapper

import uz.gita.common.data.AuthData
import uz.gita.common.data.CardData
import uz.gita.common.data.HomeData
import uz.gita.data.model.request.AuthRequest
import uz.gita.data.model.request.CardRequest
import uz.gita.data.model.request.HomeRequest
import uz.gita.data.model.response.CardResponse
import uz.gita.data.model.response.HomeResponse

//Auth
fun AuthData.SignUp.toRequest(): AuthRequest.SignUp =
    AuthRequest.SignUp(phone, password, firstName, lastName, birthDate, gender)

fun AuthData.SignIn.toRequest(): AuthRequest.SignIn = AuthRequest.SignIn(phone, password)



//Home
fun HomeResponse.TotalBalance.toResponse():HomeData.TotalBalance = HomeData.TotalBalance(totalBalance)
fun HomeResponse.BasicInfo.toResponse():HomeData.BasicInfo = HomeData.BasicInfo(firsName, genderType, age)
fun HomeResponse.FullInfo.toResponse():HomeData.FullInfo = HomeData.FullInfo(firstName, lastName, phone, birthDate, gender)
fun HomeResponse.UpdateInfo.toResponse():HomeData.UpdateInfoResponse = HomeData.UpdateInfoResponse(message)
fun HomeResponse.TransferInfo.toResponse():HomeData.TransferInfo = HomeData.TransferInfo(type, from, to, amount, time)

fun HomeData.UpdateInfo.toRequest():HomeRequest.UpdateInfo = HomeRequest.UpdateInfo(firstName, lastName, genderType, bornDate)



//Card
fun CardResponse.CardMessage.toResponse():CardData.CardMessage = CardData.CardMessage(message)
fun CardResponse.CardInfo.toResponse():CardData.CardInfo = CardData.CardInfo(id, name, amount, owner, pan, expiredYear, expiredMonth, themeType, isVisible)

fun CardData.NewCardParams.toRequest():CardRequest.AddCard = CardRequest.AddCard(pan, expiredYear, expiredMonth, name)
fun CardData.UpdateCardParams.toRequest():CardRequest.UpdateCard = CardRequest.UpdateCard(id, name, themeType, isVisible)


