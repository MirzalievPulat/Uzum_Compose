package uz.gita.uzumcompose.data.locale

import android.content.Context

class LocalStorage (context: Context) : SharedPreferenceHelper(context) {
    var isFirstRun: Boolean by booleans(true)
    var isSignIn: Boolean by booleans(false)
    var token: String by strings("")
    var refreshToken: String by strings("")
    var accessToken: String by strings("")

    var pin: String by strings("")

    var userId: Int by ints()
    var parentId: Int by ints()
    var username: String by strings()
    var phone: String by strings()
}