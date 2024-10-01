package uz.gita.common.data


sealed interface HomeData {
    data class TotalBalance(val balance:String)
    data class BasicInfo(
        val firsName: String,
        val genderType: String,
        val age: String
    )

    data class FullInfo(
        val firstName: String,
        val lastName: String,
        val phone: String,
        val birthDate: String,
        val gender: String
    )

    data class UpdateInfo(
        val firstName: String,
        val lastName: String,
        val genderType: String,
        val bornDate: String
    )


    data class UpdateInfoResponse(val message: String)

    data class TransferInfo(
        val type: String,
        val from: String,
        val to: String,
        val amount: String,
        val time: String
    )
}