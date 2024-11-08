package uz.gita

class Random {
    external fun getInfo(): String
//    external fun sqr(x: Int): Int

    companion object {
        init {
            System.loadLibrary("bootcamp")
        }
    }
}