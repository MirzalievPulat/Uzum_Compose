package uz.gita.entity.locale

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.gita.common.other.AfterSplash
import javax.inject.Inject


class LocalStorage @Inject constructor(@ApplicationContext context: Context) : SharedPreferenceHelper(context) {

    var token: String by strings("")
    var refreshToken: String by strings("")
    var accessToken: String by strings("")

    var pin: String by strings("")
    var lastTotalBalance: String by strings("0")
    var cardList:String by strings("[]")

    var phone: String by strings()
    var name: String by strings("how are you")
    var isMoneyVisible: Boolean by booleans(true)

    var transferringAmount:String by strings("0")
    var transferringPan:String by strings("")

    private var _afterSplash: String by strings(AfterSplash.SIGN_UP.name)
    var afterSplash: AfterSplash
        get() = AfterSplash.valueOf(_afterSplash)
        set(value) {
            _afterSplash = value.name
        }
}

