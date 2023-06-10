package com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.ExpanseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddEditExpanse @Inject constructor(
    private val expanseUseCases: ExpanseUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val sdf = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
    private val _date = mutableStateOf(
        ExpanseTextFieldState(
            hint = "Date",
            text = sdf.format(Date()).toString()
        )
    )
    val date: State<ExpanseTextFieldState> = _date

    private val _amount = mutableStateOf(
        ExpanseTextFieldState(
            hint = "amount"
        )
    )
    val amount: State<ExpanseTextFieldState> = _amount

    private val _category = mutableStateOf(
        ExpanseTextFieldState(
            hint = "category"
        )
    )
    val category: State<ExpanseTextFieldState> = _category

    private val _paymentMode = mutableStateOf(
        ExpanseTextFieldState(
            hint = "payment mode"
        )
    )
    val paymentMode: State<ExpanseTextFieldState> = _paymentMode
}