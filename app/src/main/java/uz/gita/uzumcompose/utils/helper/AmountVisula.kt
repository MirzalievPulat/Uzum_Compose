package uz.gita.uzumcompose.utils.helper

import android.util.Log
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
//    10 000 000   1 000 000 000  100 000    10 000  1 000  100
class AmountVisual:VisualTransformation{
    override fun filter(text: AnnotatedString): TransformedText {

        val len = text.length
        val sb = StringBuilder(text)
        text.chunked(3){

        }
        if (len > 3){
            var remainder = when(len%3){
                1-> 1
                2-> 2
                else -> {3}
            }
            repeat((len-1)/3){
                sb.insert(remainder," ")
                remainder += 4
            }

            return  TransformedText(AnnotatedString(sb.toString()),MyOffsetMapper)
        }else return TransformedText(text,MyOffsetMapper)
    }
}

object MyOffsetMapper:OffsetMapping{
    override fun originalToTransformed(offset: Int): Int {
//            Log.d("TAG", "originalToTransformed: offset:$offset")
        val dd = (offset-1)/3
        return offset+dd
    }

    override fun transformedToOriginal(offset: Int): Int {
//        Log.d("TAG", "originalToTransformed: offset:$offset")
        val dd = (offset-1)/3
        return offset-dd
    }

}