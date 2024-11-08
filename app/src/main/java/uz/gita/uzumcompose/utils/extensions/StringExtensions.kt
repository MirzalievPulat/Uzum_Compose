package uz.gita.uzumcompose.utils.extensions

import android.util.Log
import uz.gita.uzumcompose.R
import java.text.NumberFormat
import java.util.Locale

fun String.makeReadable():String{
    val temp = StringBuilder(this)
    if(this.isNotBlank()){
        temp.insert(4," ")
        temp.insert(7," ")
        temp.insert(11,"-")
        temp.insert(14,"-")
    }
    return temp.toString()
}

fun formatToMoney(value: Int): String {
    val formatter = NumberFormat.getInstance(Locale.FRANCE)
    return formatter.format(value)
}

fun formatToMoney(value: String): String {
    val number = value.toIntOrNull() ?: 0

    val formatter = NumberFormat.getInstance(Locale.FRANCE)
    return formatter.format(number)
}

fun String.toPrivatePan():String{
    val temp = StringBuilder(this)
    if (this.isNotBlank()){
        for (i in 6..11){
            temp.setCharAt(i,'·')
        }

        temp.insert(4," ")
        temp.insert(9," ")
        temp.insert(14," ")
    }
    return temp.toString()
}


fun String.toCardImage():Int{
    return when(this){
        "1"-> R.drawable.ic_custom_card_bg_1
        "2"-> R.drawable.ic_custom_card_bg_2
        "3"-> R.drawable.ic_limit_card
        "4"-> R.drawable.bg_uzum_card_44
        else -> {
            R.drawable.card_green}
    }
}




fun main() {
}