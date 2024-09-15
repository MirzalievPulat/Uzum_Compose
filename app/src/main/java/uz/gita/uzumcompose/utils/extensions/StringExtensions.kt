package uz.gita.uzumcompose.utils.extensions

fun String.makeReadable():String{
    val temp = StringBuilder(this)
    temp.insert(4," ")
    temp.insert(7," ")
    temp.insert(11,"-")
    temp.insert(14,"-")
    return temp.toString()
}

fun main() {
    println("+998936683105".makeReadable())
    println("polat")
}