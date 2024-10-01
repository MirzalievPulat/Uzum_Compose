package uz.gita.uzumcompose.screens.pages.message

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.components.AppTextField
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.TextField
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum

class MessagePage:Tab {
    override val options: TabOptions
        @Composable
        get(){
            val title = stringResource(R.string.bottom_nav_messages)
            val icon = rememberVectorPainter(image = ImageVector.vectorResource(R.drawable.ic_message))

            return TabOptions(
                index = 3u,
                title = title,
                icon = icon
            )
        }

    @Composable
    override fun Content() {
        Text(text = "Messages page")
    }
}

@Preview
@Composable
fun MessagePagePrev() {

    UzumComposeTheme {
        MessagePageContent()
    }
}

@Composable
fun MessagePageContent() {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(text = stringResource(R.string.txt_customer_support),
                    fontFamily = fontFamilyUzum,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                    )

                Text(text = stringResource(R.string.txt_online),
                    fontFamily = fontFamilyUzum,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.HintUzum
                    )
            }
        }
    ) {contentPadding->

        var message by remember { mutableStateOf("a") }

        Column(modifier = Modifier
            .padding(contentPadding)
            .background(color = Color.White)
            .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = stringResource(R.string.txt_customer_support_info),
                fontFamily = fontFamilyUzum,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.HintUzum,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.Bottom) {
                Box(modifier = Modifier
                    .size(40.dp)
                    .background(color = Color.TextField, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ){
                    Icon(painter = painterResource(id = R.drawable.ic_attach_file_24),
                        contentDescription = "Media")
                }

                AppTextField(hint = "Write a message",
                    value = message,
                    onValueChange = {message = it},
                    trailingIcon = {
                        if (message.isNotBlank()){
                            Image(painter = painterResource(id = R.drawable.ic_send_24_fill),
                                contentDescription = "Send",
                                modifier = Modifier.padding(horizontal = 8.dp)
                                    .clip(CircleShape)
                                    .clickable {

                                    })
                        }
                    }
                )
            }
        }
    }
}

