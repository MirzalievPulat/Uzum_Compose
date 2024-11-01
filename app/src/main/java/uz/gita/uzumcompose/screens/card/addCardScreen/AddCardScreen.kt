package uz.gita.uzumcompose.screens.card.addCardScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.presentation.cards.addCard.AddCardContract
import uz.gita.presentation.cards.addCard.AddCardVM
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.components.AppButton
import uz.gita.uzumcompose.ui.components.AppTextField
import uz.gita.uzumcompose.ui.components.CardSuccessDialog
import uz.gita.uzumcompose.ui.components.NetworkErrorDialog
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.ui.theme.fontUzumPro
import uz.gita.uzumcompose.utils.helper.MaskVisualTransformation


class AddCardScreen : Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: AddCardContract.ViewModel = getViewModel<AddCardVM>()
        viewModel.collectSideEffect {
            if (it is AddCardContract.SideEffect.ToastMessage) {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        AddCardScreenContent(viewModel.collectAsState(), viewModel::onEventDispatcher)
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    UzumComposeTheme {
        AddCardScreenContent(remember { mutableStateOf(AddCardContract.UIState()) }, {})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreenContent(
    uiState: State<AddCardContract.UIState>,
    onEventDispatcher: (AddCardContract.Intent) -> Unit
) {

    if (uiState.value.dialogOpen) {
        CardSuccessDialog { onEventDispatcher(AddCardContract.Intent.DialogOKClick) }
    }

    if (uiState.value.networkDialog) {
        NetworkErrorDialog { onEventDispatcher(AddCardContract.Intent.NetworkDialogDismiss) }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back arrow",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(
                            onClick = {
                                onEventDispatcher(AddCardContract.Intent.Back)
                            }
                        )
                        .padding(8.dp)
                )

                Text(
                    text = stringResource(id = R.string.txt_new_card),
                    color = Color.BlackUzum,
                    fontSize = 16.sp,
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {

            var pan by remember { mutableStateOf("") }
            var yearMonth by remember { mutableStateOf("") }
            var name by remember { mutableStateOf("") }

            AppTextField(
                hint = "Card number",
                value = pan,
                onValueChange = { if (it.length <= 16 && !it.contains(regex = Regex("[ .,\\-]"))) pan = it },
                modifier = Modifier.padding(16.dp),
                trailingIcon = {
                    if (pan.isEmpty()) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_24_scan_card),
                            contentDescription = "Card scan",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    } else {
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
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),

                errorText = uiState.value.errorPan
            )

            AppTextField(
                hint = "MM/YY",
                value = yearMonth,
                onValueChange = { if (it.length <= 4 && !it.contains(regex = Regex("[ .,\\-]"))) yearMonth = it },
                modifier = Modifier
                    .padding(16.dp)
                    .width(200.dp),
                visualTransformation = MaskVisualTransformation("##/##"),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                errorText = uiState.value.errorMonthYear
            )

            AppTextField(
                hint = "Card name",
                value = name,
                onValueChange = { if (it.length <= 30) name = it },
                modifier = Modifier
                    .padding(16.dp)
                    .width(200.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                errorText = uiState.value.errorName
            )

            Row(
                modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tick_purple),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(28.dp)
                )

                Text(
                    text = stringResource(R.string.tick_text),
                    fontFamily = fontFamilyUzum,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
            Row(
                modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.card_green),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(28.dp)
                )

                Text(
                    text = stringResource(R.string.card_text),
                    fontFamily = fontFamilyUzum,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            AppButton(
                text = stringResource(R.string.txt_next),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                showProgress = uiState.value.isLoading
            ) {
                onEventDispatcher.invoke(
                    AddCardContract.Intent.NextClick(
                        pan, yearMonth, name
                    )
                )
            }
        }
    }
}