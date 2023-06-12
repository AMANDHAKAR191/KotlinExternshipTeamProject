package com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse

import ChipInputField
import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.componants.CustomTextField
import java.util.Calendar
import java.util.Date

@Composable
fun AddEditExpanseScreen(
    viewModel: AddEditExpanseViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit
) {
    val dateState = viewModel.date.value
    val amountState = viewModel.amount.value
    val categoryState = viewModel.category.value
    val paymentModeState = viewModel.paymentMode.value
    val tagsState = viewModel.tags.value
    val noteState = viewModel.note.value
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //
        // Fetching the Local Context
        val mContext = LocalContext.current

        // Declaring integer values
        // for year, month and day
        val mYear: Int
        val mMonth: Int
        val mDay: Int

        // Initializing a Calendar
        val mCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        mCalendar.time = Date()

        // Declaring a string value to
        // store date in string format
        val mDate = remember { mutableStateOf("") }

        // Declaring DatePickerDialog and setting
        // initial values as current values (present year, month and day)
        val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
            }, mYear, mMonth, mDay
        )


        Text(text = "Add Edit Screen")
        CustomTextField(
            text = dateState.text,
            label = dateState.hint,
            onValueChange = {},
            enabled = false,
            leadingIcon = { Icons.Default.Home },
            trailingIcon = { Icons.Default.ArrowDropDown },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            onClick = {
                Toast.makeText(context, "DatePicking..", Toast.LENGTH_SHORT).show()
                mDatePickerDialog.show()
                mDatePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                    mDate.value = "$dayOfMonth/${mMonth + 1}/$mYear"
                    viewModel.onEvent(AddEditExpanseEvent.EnteredDate(mDate.value))
                }

            }
        )
        CustomTextField(
            text = amountState.text,
            label = amountState.hint,
            onValueChange = { viewModel.onEvent(AddEditExpanseEvent.EnteredAmount(it)) },
            enabled = true,
            leadingIcon = { Icons.Default.Home },
            trailingIcon = { Icons.Default.Close },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            onTrailingIconClick = {
                viewModel.onEvent(AddEditExpanseEvent.EnteredAmount(""))
            },
            onClick = { Toast.makeText(context, "Clicked..", Toast.LENGTH_SHORT).show() }
        )

        CustomTextField(
            text = categoryState.text,
            label = categoryState.hint,
            onValueChange = { viewModel.onEvent(AddEditExpanseEvent.EnteredAmount(it)) },
            enabled = false,
            leadingIcon = { Icons.Default.Home },
            trailingIcon = { Icons.Default.ArrowDropDown },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            onClick = { Toast.makeText(context, "Clicked..", Toast.LENGTH_SHORT).show() }
        )
        CustomTextField(
            text = paymentModeState.text,
            label = paymentModeState.hint,
            onValueChange = { viewModel.onEvent(AddEditExpanseEvent.EnteredAmount(it)) },
            enabled = false,
            leadingIcon = { Icons.Default.Home },
            trailingIcon = { Icons.Default.ArrowDropDown },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            onClick = { Toast.makeText(context, "Clicked..", Toast.LENGTH_SHORT).show() }
        )
        ChipInputField(
            tags = ListStringConverter().toListString(tagsState.tagsList),
            onSpacePressed = {
                println("tags: $it")
                viewModel.onEvent(
                    AddEditExpanseEvent.EnteredTags(
                        ListStringConverter().fromListString(
                            it
                        )
                    )
                )
            })
        CustomTextField(
            text = noteState.text,
            label = noteState.hint,
            onValueChange = { viewModel.onEvent(AddEditExpanseEvent.EnteredNote(it)) },
            enabled = true,
            leadingIcon = { Icons.Default.Home },
            trailingIcon = { Icons.Default.Close },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            onTrailingIconClick = {
                viewModel.onEvent(AddEditExpanseEvent.EnteredAmount(""))
            },
            onClick = { Toast.makeText(context, "Clicked..", Toast.LENGTH_SHORT).show() }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                viewModel.onEvent(AddEditExpanseEvent.SaveNote)
                navigateToHomeScreen()
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        ) {
            Text(text = "Save")
        }

    }
}