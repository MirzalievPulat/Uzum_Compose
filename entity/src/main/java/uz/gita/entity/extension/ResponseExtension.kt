package uz.gita.entity.extension

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import uz.gita.entity.model.response.ErrorMessage

private val gson = Gson()

suspend fun <T, R> Response<T>.toResult(successBlock: suspend (T) -> R): Result<R> {
    if (code()>=500){
        Log.d("TAG", "toResult: body: ${body()} errorBody:${errorBody()?.string()}")
        return Result.failure(Exception("Serverda xatolik"))
    }
    return when {
        isSuccessful && body() != null -> {
            Result.success(successBlock(body()!!))
        }


        errorBody() != null -> {
//            val type = object :TypeToken<ErrorMessage>(){}.type
//            val errorResponse = gson.fromJson<ErrorMessage>(errorBody()!!.string(), type)//here

            val errorResponse = gson.fromJson(errorBody()!!.string(), ErrorMessage::class.java)
            Log.d("TAG", "toResult: errorResponse:$errorResponse")
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
