package com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.componants.CustomTextField
import java.util.Calendar
import java.util.Date

@Composable
fun AddEditExpanseScreen(
    viewModel: AddEditExpanseViewModel = hiltViewModel(),
    navigateToProfileScreen: () -> Unit
) {
    val amountState = viewModel.amount.value
    val categoryState = viewModel.category.value
    val paymentModeState = viewModel.paymentMode.value
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
//


        Text(text = "Add Edit Screen")
        CustomTextField(
            text = amountState.text,
            label = amountState.hint,
            onValueChange = { viewModel.onEvent(AddEditExpanseEvent.EnteredAmount(it)) },
            enabled = true,
            leadingIcon = { Icons.Default.Home },
            trailingIcon = { Icons.Default.Close },
            onClick = {
                Toast.makeText(context, "Clicked..", Toast.LENGTH_SHORT).show()
            })
        CustomTextField(
            text = mDate.value,
            label = "Date",
            onValueChange = { viewModel.onEvent(AddEditExpanseEvent.EnteredAmount(mDate.value)) },
            enabled = false,
            leadingIcon = { Icons.Default.Home },
            trailingIcon = { Icons.Default.ArrowRight },
            onClick = {
                Toast.makeText(context, "DatePicking..", Toast.LENGTH_SHORT).show()
                mDatePickerDialog.show()
            })
        CustomTextField(
            text = categoryState.text,
            label = categoryState.hint,
            onValueChange = { viewModel.onEvent(AddEditExpanseEvent.EnteredAmount(it)) },
            enabled = false,
            leadingIcon = { Icons.Default.Home },
            trailingIcon = { Icons.Default.ArrowDropDown },
            onClick = {
                Toast.makeText(context, "Clicked..", Toast.LENGTH_SHORT).show()
            })
        CustomTextField(
            text = paymentModeState.text,
            label = paymentModeState.hint,
            onValueChange = { viewModel.onEvent(AddEditExpanseEvent.EnteredAmount(it)) },
            enabled = false,
            leadingIcon = { Icons.Default.Home },
            trailingIcon = { Icons.Default.ArrowDropDown },
            onClick = {
                Toast.makeText(context, "Clicked..", Toast.LENGTH_SHORT).show()
            })
        CustomTextField(
            text = paymentModeState.text,
            label = paymentModeState.hint,
            onValueChange = { viewModel.onEvent(AddEditExpanseEvent.EnteredAmount(it)) },
            enabled = false,
            leadingIcon = { Icons.Default.Home },
            trailingIcon = { Icons.Default.ArrowDropDown },
            onClick = {
                Toast.makeText(context, "Clicked..", Toast.LENGTH_SHORT).show()
            })
        CustomTextField(
            text = paymentModeState.text,
            label = paymentModeState.hint,
            onValueChange = { viewModel.onEvent(AddEditExpanseEvent.EnteredAmount(it)) },
            enabled = false,
            leadingIcon = { Icons.Default.Home },
            trailingIcon = { Icons.Default.ArrowDropDown },
            onClick = {
                Toast.makeText(context, "Clicked..", Toast.LENGTH_SHORT).show()
            })

    }
}