package uz.gita.uzumcompose.screens.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.common.data.HomeData
import uz.gita.presentation.cards.updateCard.UpdateCardContract
import uz.gita.presentation.cards.updateCard.UpdateCardVM
import uz.gita.presentation.helper.extensions.toDate
import uz.gita.presentation.profile.ProfileContract
import uz.gita.presentation.profile.ProfileVM
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.screens.card.updateCard.UpdateCardContent
import uz.gita.uzumcompose.ui.components.AppButton
import uz.gita.uzumcompose.ui.components.AppRadioButton
import uz.gita.uzumcompose.ui.components.DatePickerProfileModal
import uz.gita.uzumcompose.ui.components.NetworkErrorDialog
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.TransparentGray
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.VeryPlainGray
import uz.gita.uzumcompose.ui.theme.WhiteBg
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.ui.theme.fontUzumPro

class ProfileScreen() : Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: ProfileContract.ViewModel = getViewModel<ProfileVM>()
        viewModel.collectSideEffect {
            if (it is ProfileContract.SideEffect.ToastMessage) {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        ProfileScreenContent(viewModel.collectAsState(), viewModel::onEventDispatcher)
    }
}

@Preview
@Composable
fun ProfileScreenPrev() {
    UzumComposeTheme {
        ProfileScreenContent(
            remember { mutableStateOf(ProfileContract.UIState()) }, {}
        )
    }
}

@Composable
fun ProfileScreenContent(
    uiState: State<ProfileContract.UIState>,
    onEventDispatcher: (ProfileContract.Intent) -> Unit
) {

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.WhiteBg,
            darkIcons = true
        )
    }


    if (uiState.value.isLoading){
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.TransparentGray),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color.PinkUzum,
            )
        }
    }

    if (uiState.value.networkDialog) {
        NetworkErrorDialog { onEventDispatcher(ProfileContract.Intent.NetworkDialogDismiss) }
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .background(color = Color.WhiteBg),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { onEventDispatcher(ProfileContract.Intent.Back) }
                            .padding(16.dp)

                    )

                    Text(
                        text = stringResource(R.string.profile),
                        color = Color.BlackUzum,
                        fontSize = 18.sp,
                        fontFamily = fontUzumPro,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }

        },
        floatingActionButton = {
            AppButton(
                enabled = uiState.value.buttonEnabled,
                showProgress = uiState.value.saveLoading,
                text = stringResource(R.string.save),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(56.dp),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                ),
            ) {
                onEventDispatcher(ProfileContract.Intent.SaveClick)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.WhiteBg),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier
                        .padding(32.dp)
                        .size(40.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = uiState.value.surname.uppercase(),
                        color = Color.BlackUzum,
                        fontSize = 22.sp,
                        fontFamily = fontFamilyUzum,
                        lineHeight = 28.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                    )

                    Text(
                        text = uiState.value.name.uppercase(),
                        color = Color.BlackUzum,
                        fontSize = 22.sp,
                        fontFamily = fontFamilyUzum,
                        lineHeight = 28.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                    )
                }


            }

            Column(
                modifier = Modifier
                    .background(color = Color.WhiteBg)
                    .fillMaxWidth()
                    .background(
                        color = Color.White, shape = RoundedCornerShape(
                            topEnd = 24.dp,
                            topStart = 24.dp
                        )
                    )
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.personal_data),
                    color = Color.BlackUzum,
                    fontSize = 24.sp,
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 24.dp)
                )

                Text(
                    text = stringResource(R.string.tf_last_name),
                    color = Color.BlackUzum,
                    fontSize = 16.sp,
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                OutlinedTextField(
                    value = uiState.value.surname,
                    onValueChange = { onEventDispatcher(ProfileContract.Intent.SurNameChange(it)) },
                    textStyle = TextStyle(
                        color = Color.BlackUzum,
                        fontSize = 18.sp,
                        fontFamily = fontUzumPro,
                        fontWeight = FontWeight.Medium,
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.WhiteBg,
                        focusedIndicatorColor = Color.PinkUzum,
                        unfocusedIndicatorColor = Color.VeryPlainGray,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Text(
                    text = stringResource(R.string.tf_first_name),
                    color = Color.BlackUzum,
                    fontSize = 16.sp,
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 4.dp, top = 16.dp)
                )

                OutlinedTextField(
                    value = uiState.value.name,
                    onValueChange = {onEventDispatcher(ProfileContract.Intent.NameChange(it))},
                    textStyle = TextStyle(
                        color = Color.BlackUzum,
                        fontSize = 18.sp,
                        fontFamily = fontUzumPro,
                        fontWeight = FontWeight.Medium,
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.WhiteBg,
                        focusedIndicatorColor = Color.PinkUzum,
                        unfocusedIndicatorColor = Color.VeryPlainGray,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()

                )

                Text(
                    text = stringResource(R.string.txt_date_of_birth),
                    color = Color.BlackUzum,
                    fontSize = 16.sp,
                    fontFamily = fontUzumPro,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 4.dp, top = 16.dp)
                )

                DatePickerProfileModal(birthDate = uiState.value.birthDate.toDate(),
                    onDateSelected = {
                        onEventDispatcher(ProfileContract.Intent.BirthDateChange(it))
                    }
                )


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 72.dp)
                ) {
                    AppRadioButton(
                        selected = uiState.value.isMale,
                        onClick = { onEventDispatcher(ProfileContract.Intent.MaleClick(true)) },
                        text = stringResource(R.string.txt_male)
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    AppRadioButton(
                        selected = !uiState.value.isMale,
                        onClick = { onEventDispatcher(ProfileContract.Intent.MaleClick(false)) },
                        text = stringResource(R.string.txt_female)
                    )
                }

            }
        }
    }

}