package com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.ExpanseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddEditExpanseViewModel @Inject constructor(
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


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentExpanseId: Int? = null

    init {
        savedStateHandle.get<Int>("expanseId")?.let { expanseId ->
            if (expanseId != -1) {
                viewModelScope.launch {
                    expanseUseCases.getExpanse(expanseId)?.also { expanse ->
                        currentExpanseId = expanse.id
                        _date.value = date.value.copy(
                            text = expanse.date,
                            isHintVisible = false
                        )
                        _amount.value = amount.value.copy(
                            text = expanse.amount,
                            isHintVisible = false
                        )
                        _category.value = category.value.copy(
                            text = expanse.category,
                            isHintVisible = false
                        )
                        _paymentMode.value = paymentMode.value.copy(
                            text = expanse.paymentMode,
                            isHintVisible = false
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditExpanseEvent) {
        when (event) {
            is AddEditExpanseEvent.EnteredAmount -> {
                _amount.value = amount.value.copy(
                    text = event.value
                )
            }

            is AddEditExpanseEvent.ChangeAmountFocus -> {
                _amount.value = amount.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            amount.value.text.isBlank()
                )
            }

            else -> {}
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}