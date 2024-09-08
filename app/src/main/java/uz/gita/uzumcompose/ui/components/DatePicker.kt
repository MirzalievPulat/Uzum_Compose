package uz.gita.uzumcompose.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import uz.gita.uzumcompose.R
import uz.gita.uzumcompose.ui.theme.BlackUzum
import uz.gita.uzumcompose.ui.theme.HintUzum
import uz.gita.uzumcompose.ui.theme.TextField
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.ui.theme.fontFamilyUzum
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        selectableDates = PastOrPresentSelectableDates,
        initialSelectedDateMillis = System.currentTimeMillis(),
    )
    val isDatePickerShowed = remember { mutableStateOf(false) }
    val s = stringResource(R.string.txt_date_of_birth)
    val textDate = remember { mutableStateOf(s) }

    if (isDatePickerShowed.value) {
        DatePickerDialog(
            onDismissRequest = {
                isDatePickerShowed.value = false
            },
            confirmButton = {
                TextButton(onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    textDate.value = datePickerState.selectedDateMillis?.let { convertMillisToDate(it) }.toString()
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
            DatePicker(state = datePickerState,
                showModeToggle = false,
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color = Color.TextField)
            .height(50.dp)
            .padding(start = 12.dp, end = 6.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    isDatePickerShowed.value = true
                }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = textDate.value,
            style = TextStyle(
                fontFamily = fontFamilyUzum,
                fontWeight = FontWeight.Medium,
                color = if (textDate.value == s) Color.HintUzum else Color.BlackUzum,
                fontSize = 14.sp,
            ),
            modifier = Modifier
                .weight(1f)
        )

        Icon(
            imageVector = Icons.Rounded.DateRange,
            contentDescription = "icon date",
            modifier = Modifier
                .clip(CircleShape)
                .clickable { isDatePickerShowed.value = true }
                .padding(8.dp)
        )

    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Preview
@Composable
private fun DatePickerModalPreview() {
    UzumComposeTheme {
        DatePickerModal(onDateSelected = {

        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
object PastOrPresentSelectableDates : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis <= System.currentTimeMillis()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun isSelectableYear(year: Int): Boolean {
        return year <= LocalDate.now().year
    }
}