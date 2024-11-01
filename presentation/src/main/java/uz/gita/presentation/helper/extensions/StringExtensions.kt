package uz.gita.presentation.helper.extensions

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
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

fun String.toDate():String{
    return SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(Date(this.toLong()))
}

fun String.toTime():String{
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(this.toLong()))
}

fun main() {
    println("1190660400000".toDate())
}