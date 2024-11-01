package uz.gita.uzumcompose.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastCbrt
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.PinkUzum
import uz.gita.uzumcompose.ui.theme.TextField
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.VeryPlainGray
import uz.gita.uzumcompose.ui.theme.WhiteBg
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import uz.gita.uzumcompose.ui.theme.fontUzumPro
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerProfileModal(
    birthDate:String,
    onDateSelected: (Long?) -> Unit,
    errorText: String? = null
) {
    val datePickerState = rememberDatePickerState(
        selectableDates = PastOrPresentSelectableDates,
        initialSelectedDateMillis = System.currentTimeMillis(),
    )
    val isDatePickerShowed = remember { mutableStateOf(false) }

    if (isDatePickerShowed.value) {
        DatePickerDialog(
            onDismissRequest = {
                isDatePickerShowed.value = false
            },
            confirmButton = {
                TextButton(onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    isDatePickerShowed.value = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { isDatePickerShowed.value = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false,
            )
        }
    }

    OutlinedTextField(
        value = birthDate,
        onValueChange = {
//            if (it.length < 30) it
        },
        textStyle = TextStyle(
            color = Color.BlackUzum,
            fontSize = 18.sp,
            fontFamily = fontUzumPro,
            fontWeight = FontWeight.Medium,
        ),
        shape = RoundedCornerShape(16.dp),
        trailingIcon = {
            Icon(
                imageVector = Icons.Rounded.DateRange,
                contentDescription = "icon date",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { isDatePickerShowed.value = true }
                    .padding(8.dp)
            )
        },
        enabled = false,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.WhiteBg,
            focusedIndicatorColor = Color.PinkUzum,
            unfocusedIndicatorColor = Color.VeryPlainGray,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isDatePickerShowed.value = true }

    )

}

//fun convertMillisToDate(millis: Long): String {
//    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//    return formatter.format(Date(millis))
//}

@Preview
@Composable
private fun DatePickerProfilePreview() {
    UzumComposeTheme {
        DatePickerProfileModal(birthDate = "12/12/2002",onDateSelected = {

        })
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//object PastOrPresentSelectableDates : SelectableDates {
//    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
//        return utcTimeMillis <= System.currentTimeMillis()
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun isSelectableYear(year: Int): Boolean {
//        return year <= LocalDate.now().year
//    }
//}