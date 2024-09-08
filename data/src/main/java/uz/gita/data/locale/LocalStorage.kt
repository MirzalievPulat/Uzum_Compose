package uz.gita.data.locale

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class LocalStorage  @Inject constructor(@ApplicationContext context: Context) : SharedPreferenceHelper(context) {
//    var isFirstRun: Boolean by booleans(true)
//    var isSignIn: Boolean by booleans(false)
    var token: String by strings("")
    var refreshToken: String by strings("")
    var accessToken: String by strings("")

    var pin: String by strings("")
    var afterSplash: Int by ints(0)

//    var userId: Int by ints()
//    var parentId: Int by ints()
//    var username: String by strings()
    var phone: String by strings()
}