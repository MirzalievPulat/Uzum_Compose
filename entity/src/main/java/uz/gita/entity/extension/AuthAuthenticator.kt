package uz.gita.entity.extension

import android.content.Context
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import uz.gita.common.direction.LogOutDirection
import uz.gita.common.other.AfterSplash
import uz.gita.entity.locale.LocalStorage
import uz.gita.entity.model.request.AuthRequest
import uz.gita.entity.remote.api.AuthApi
import uz.gita.entity.repository.AuthRepository
import javax.inject.Inject



class AuthAuthenticator @Inject constructor(
    private val localStorage: LocalStorage,
    private val oAuthApi: AuthApi,
    private val appRepository: AuthRepository,
    private val logOutDirection: LogOutDirection,
    @ApplicationContext private val context:Context
) : Authenticator {

    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val currentToken = runBlocking { localStorage.accessToken }
        synchronized(this) {
            Log.d("TTT", "authenticate: synchronized ichi")
            val updatedToken = runBlocking { localStorage.accessToken }
            var token = if (currentToken != updatedToken) updatedToken
            else {
                val newSessionResponse = runBlocking { oAuthApi.updateToken(AuthRequest.UpdateToken(localStorage.refreshToken)) }
                if (newSessionResponse.isSuccessful && newSessionResponse.body() != null) {
                    Log.d("TTT", "authenticate success: synchronized ichi")
                    newSessionResponse.body()?.let { body ->
                        runBlocking {
                            localStorage.accessToken = body.accessToken
                            localStorage.refreshToken = body.refreshToken
                        }
                        body.accessToken
                    }
                } else null
            }
            if (token == null&&localStorage.refreshToken!="") {
                runBlocking {
                    Log.d("TTT", "authenticate: token = null")
                    appRepository.logOut().collect{
                        it.onSuccess { logOutDirection.logOut() }
                        it.onFailure { Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show() }
                    }
                }
            }
            return if (token != null) response.request.newBuilder()
                .header(HEADER_AUTHORIZATION, "$TOKEN_TYPE $token")
                .build() else null
        }
    }
}