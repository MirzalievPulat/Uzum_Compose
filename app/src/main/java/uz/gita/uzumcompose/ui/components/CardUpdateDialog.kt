package uz.gita.uzumcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import uz.gita.presentation.cards.updateCard.UpdateCardContract
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardUpdateDialog(
    onDismissRequest: () -> Unit,
    yesClick: ()->Unit,
    noClick: ()->Unit,
    showProgress:Boolean
) {
    BasicAlertDialog(onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(32.dp)
                ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.orange_success
                ),
                contentDescription = "",
                modifier = Modifier.size(110.dp)
            )

            Text(
                text = stringResource(R.string.save_changes),
                fontFamily = fontFamilyUzum,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp)
            )

            Row (
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                AppButton(text = stringResource(R.string.no),
                    modifier = Modifier
                        .padding(top = 32.dp, bottom = 32.dp)
                        .wrapContentWidth(),
                    onClick = {
                        noClick()
                        onDismissRequest()
                    }
                )
                Spacer(modifier = Modifier.width(32.dp))
                AppButton(text = stringResource(R.string.yes),
                    modifier = Modifier
                        .padding(top = 32.dp, bottom = 32.dp)
                        .wrapContentWidth(),
                    onClick = {
                        yesClick()
                        onDismissRequest()
                    },
                    showProgress = showProgress
                )
            }


        }
    }
}

@Preview
@Composable
fun CardUpdatePrev() {
    Column {
        CardUpdateDialog({},{},{},true)
    }
}

