package uz.gita.uzumcompose.utils.helper

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class Amount2Visual: VisualTransformation {
    var text = ""
    override fun filter(text: AnnotatedString): TransformedText {
        this.text = text.toString()
        val formattedText = AnnotatedString(text.toString().toFormat(3))

        return TransformedText(formattedText, AmountOffsetMapper())
    }
    class AmountOffsetMapper: OffsetMapping {
        override fun originalToTransformed(offset: Int): Int = (offset - 1) / 3 + offset

        override fun transformedToOriginal(offset: Int): Int = offset-offset/4
    }
}

private fun CharSequence.toFormat(int: Int): String {
    val sb = StringBuilder()
    for (i in indices) {
        if (i % 3 == 0 && i != 0) {
            sb.append(" ")
        }
        sb.append(this[i])
    }
    return sb.toString()
}

