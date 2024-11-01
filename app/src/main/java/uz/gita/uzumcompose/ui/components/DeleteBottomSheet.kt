package uz.gita.uzumcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.ui.theme.fontUzumPro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBottomSheet(
    showProgress:Boolean,
    yesClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = Color.White,
        dragHandle = {
            Box(modifier = Modifier
                .padding(bottom = 24.dp, top = 12.dp)
                .size(32.dp, 5.dp)
                .background(color = Color.LightGray, shape = CircleShape))
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.warning_round),
                contentDescription = "",
                Modifier.size(72.dp)
            )

            Text(
                text = stringResource(R.string.delete_the_card),
                color = Color.BlackUzum,
                fontFamily = fontUzumPro,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            Text(
                text = "Are you sure you want to delete this card?",
                color = Color.BlackUzum,
                fontFamily = fontUzumPro,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 16.sp
            )

            AppButton(
                text = stringResource(id = R.string.yes),
                modifier = Modifier
                    .padding(top = 16.dp)
//                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
                    .height(56.dp),
                textStyle = TextStyle(
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                ),
                showProgress = showProgress
                ) {
                yesClick()
            }
        }
    }
}


@Preview
@Composable
private fun DeletePreview() {
    UzumComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {

            var isVisible by remember { mutableStateOf(false) }

            Button(
                onClick = { isVisible = true },

                ) {

            }
            if (isVisible) {
                DeleteBottomSheet(
                    true,
                    {},
                    onDismissRequest = { },
                )
            }

        }
    }
}