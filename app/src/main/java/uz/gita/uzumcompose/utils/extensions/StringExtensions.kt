package uz.gita.uzumcompose.utils.extensions

import android.util.Log
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

fun main() {
    println("+998936683105".makeReadable())
    println("polat")
}