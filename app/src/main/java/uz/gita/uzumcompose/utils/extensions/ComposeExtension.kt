package uz.gita.uzumcompose.utils.extensions

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke

fun Modifier.drawDashedBorder(
    color: Color,
    strokeWidth: Float,
    cornerRadius: Float,
    dashLength: Float,
    gapLength: Float
): Modifier = this.then(
    Modifier.drawWithContent {
        // Draw the actual content of the Box first
        drawContent()

        // Drawing the dashed border
        drawRoundRect(
            color = color,
            style = Stroke(
                width = strokeWidth,
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(dashLength, gapLength), // Dash and gap lengths
                    0f // Phase shift for dashes
                )
            ),
            size = size,
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadius)
        )
    }
)

fun Context.showToast(message:String, duration:Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message, duration).show()
}
