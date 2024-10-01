package uz.gita.uzumcompose.screens.menu.card.addCardScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.presentation.auth.signUp.SignUpContract
import uz.gita.presentation.auth.signUp.SignUpVM
import uz.gita.presentation.home.HomePageContract
import uz.gita.presentation.menu.addCard.AddCardContract
import uz.gita.presentation.menu.addCard.AddCardVM
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.screens.auth.signUp.SignUpScreenContent
import uz.gita.uzumcompose.ui.components.AppTextField
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontUzumPro
import uz.gita.uzumcompose.utils.helper.MaskVisualTransformation


class AddCardScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: AddCardContract.ViewModel = getViewModel<AddCardVM>()
        AddCardScreenContent(viewModel.collectAsState(), viewModel::onEventDispatcher)
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    UzumComposeTheme {
        AddCardScreenContent(remember { mutableStateOf(AddCardContract.UIState()) },{})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreenContent(
    uiState: State<AddCardContract.UIState>,
    onEventDispatcher: (AddCardContract.Intent) -> Unit
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(7 / 1f)
                    .background(color = Color.White)
                    .padding(all = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "",
                    modifier = Modifier.size(16.dp)
                    )

                Text(
                    text = stringResource(id = R.string.txt_new_card),
                    color = Color.BlackUzum,
                    fontSize = 16.sp,
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {

            var pan by remember { mutableStateOf("") }
            var yearMonth by remember { mutableStateOf("") }

            AppTextField(
                hint = "Card number",
                value = pan,
                onValueChange = { pan = it },
                modifier = Modifier.padding(16.dp),
                trailingIcon = {
                    if (pan.isEmpty()) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_24_scan_card),
                            contentDescription = "Card scan",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }else{
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_close),
                            contentDescription = "Clear",
                            tint = Color.HintUzum,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(16.dp)
                                .clip(CircleShape)
                                .clickable {
                                    pan = ""
                                },
                        )
                    }

                },
                visualTransformation = MaskVisualTransformation("#### #### #### ####"),
                )

            AppTextField(hint = "MM/YY",
                value = yearMonth,
                onValueChange = {yearMonth = it},
                modifier = Modifier
                    .padding(16.dp)
                    .width(200.dp),
                visualTransformation = MaskVisualTransformation("##/##")
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.PinkUzum),
                onClick = {
                    onEventDispatcher.invoke(
                        AddCardContract.Intent.NextClick(
                            pan,yearMonth.substring(0,2),yearMonth.substring(3),""
                        )
                    )
                },
            ) {
                Text(text = stringResource(R.string.txt_next))
            }
        }
    }
}