package uz.gita.uzumcompose.utils.extensions

import android.util.Log
import java.text.NumberFormat
import java.util.Locale

fun String.makeReadable():String{
    val temp = StringBuilder(this)
    temp.insert(4," ")
    temp.insert(7," ")
    temp.insert(11,"-")
    temp.insert(14,"-")
    return temp.toString()
}

fun formatToMoney(value: Long): String {
    val formatter = NumberFormat.getInstance(Locale.FRANCE)
    return formatter.format(value)
}

fun formatToMoney(value: Int): String {
    val formatter = NumberFormat.getInstance(Locale.FRANCE)
    return formatter.format(value)
}

fun main() {
    println("+998936683105".makeReadable())
    println("polat")
}