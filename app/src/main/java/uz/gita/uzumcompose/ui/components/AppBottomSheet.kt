package uz.gita.uzumcompose.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.common.other.NetworkStatusValidator
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheet(
    header: String,
    networkStatusValidator: NetworkStatusValidator,
    context: Context? = null,
    infoText: String? = null,
    onDismissRequest: () -> Unit,
) {

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
    ) {
        if (networkStatusValidator.isNetworkEnabled){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
            ) {
                Text(
                    text = header,
                    color = Color.BlackUzum,
                    fontFamily = fontFamilyUzum,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Start),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )

                infoText?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = infoText,
                        color = Color.BlackUzum,
                        fontFamily = fontFamilyUzum,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.align(Alignment.Start),
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/UzumBank_Robot"))
                            context!!.startActivity(browserIntent)
                            onDismissRequest.invoke()
                        }
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_telegram_24),
                        contentDescription = "tg"
                    )
                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = stringResource(R.string.txt_write_us_via_telegram),
                        style = TextStyle(
                            fontFamily = fontFamilyUzum,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp,
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = "right",
                        tint = Color.HintUzum,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+998 78 777 07 99"))
                        context!!.startActivity(intent)
                        onDismissRequest.invoke()
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_call_24),
                            contentDescription = "tg"
                        )
                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = stringResource(R.string.txt_call),
                            style = TextStyle(
                                fontFamily = fontFamilyUzum,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp,
                            )
                        )

                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                            contentDescription = "right",
                            tint = Color.HintUzum,
                            modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 0.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_call_24),
                            contentDescription = "tg",
                            tint = Color.Transparent
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = stringResource(R.string.txt_our_number),
                            style = TextStyle(
                                fontFamily = fontFamilyUzum,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = Color.HintUzum
                            )
                        )


                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }else{
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
            ){
                Text(
                    text = header,
                    color = Color.BlackUzum,
                    fontFamily = fontFamilyUzum,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Start),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ShimmerEffect(modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    ShimmerEffect(modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    ShimmerEffect(modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ShimmerEffect(modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    ShimmerEffect(modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    ShimmerEffect(modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .clip(RoundedCornerShape(8.dp))

                    )
                }
            }
        }


    }
}


@Preview
@Composable
private fun AppPreview() {
    UzumComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {

            var isVisible by remember { mutableStateOf(false) }

            Button(
                onClick = { isVisible = true },

                ) {

            }
//            if (isVisible) {
//                AppBottomSheet(
//                    header = "Sms is not coming",
//                    onDismissRequest = { isVisible = false },
//
//                )
//            }

        }
    }
}