package uz.gita.data.extension

import com.google.gson.Gson
import retrofit2.Response
import uz.gita.data.model.response.AuthResponse
import uz.gita.data.model.response.ErrorResponse

private val gson = Gson()

suspend fun <T, R> Response<T>.toResult(successBlock: suspend (T) -> R): Result<R> {
    return when {
        isSuccessful -> {
            Result.success(successBlock(body()!!))
        }

        errorBody() != null -> {
            val errorResponse = gson.fromJson(errorBody()!!.string(), ErrorResponse::class.java)
            Result.failure(Exception(errorResponse.message))
        }

        else -> Result.failure(Exception("Unknown exception"))
    }
}

//bobur yozgani
//private fun <T> handleResult(result: Response<T>, onSuccess: (T) -> Unit): Result<Unit> {
//    return if (result.isSuccessful && result.body() != null) {
//        onSuccess(result.body()!!)
//        Result.success(Unit)
//    } else {
//        val error = gson.fromJson(result.errorBody()!!.string(), ErrorMessage::class.java);
//        Result.failure(Exception(error.message))
//    }
//}
