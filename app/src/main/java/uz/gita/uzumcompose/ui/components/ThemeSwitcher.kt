package uz.gita.uzumcompose.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uz.gita.uzumcompose.ui.theme.PinkUzum

@Composable
fun ThemeSwitcher(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = false,
    thumbPadding: Dp = 10.dp,
    size: Dp = 150.dp,
    toggleShape: Shape = CircleShape,
    onClick: () -> Unit
) {
    val offset by animateDpAsState(
        targetValue = if (darkTheme) size else 0.dp,
        animationSpec = tween(durationMillis = 300)
    )

    val colorOffSet by animateColorAsState(
        targetValue = if (darkTheme) Color.PinkUzum else Color.Gray,
        animationSpec = tween(durationMillis = 300)
    )

    Box(modifier = modifier
        .width(size * 2f)
        .height(size)
        .clip(shape = toggleShape)
        .clickable { onClick() }
        .background(color = colorOffSet)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .offset(x = offset)
                .padding(all = thumbPadding)
                .clip(shape = toggleShape)
                .background(Color.White)
        ) {}


    }
}

@Preview
@Composable
fun Prev() {
    var isDark by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        ThemeSwitcher(
            darkTheme = isDark,
            onClick = {isDark = !isDark},
            thumbPadding = 10.dp
        )
    }

}