package com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.externship.kotlinexternshipteamproject.domain.model.Expanse
import com.externship.kotlinexternshipteamproject.domain.model.InvalidExpanseException
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
            hint = "Amount"
        )
    )
    val amount: State<ExpanseTextFieldState> = _amount

    private val _category = mutableStateOf(
        ExpanseTextFieldState(
            hint = "Category"
        )
    )
    val category: State<ExpanseTextFieldState> = _category

    private val _paymentMode = mutableStateOf(
        ExpanseTextFieldState(
            hint = "Payment Mode"
        )
    )
    val paymentMode: State<ExpanseTextFieldState> = _paymentMode

    private val _tags = mutableStateOf(
        ExpanseTextFieldState(
            hint = "Tags"
        )
    )
    val tags: State<ExpanseTextFieldState> = _tags
    private val _note = mutableStateOf(
        ExpanseTextFieldState(
            hint = "Note"
        )
    )
    val note: State<ExpanseTextFieldState> = _note


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
                        _tags.value = tags.value.copy(
                            tagsList = expanse.tags,
                            isHintVisible = false
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditExpanseEvent) {
        when (event) {
            is AddEditExpanseEvent.EnteredDate -> {
                _date.value = date.value.copy(
                    text = event.value
                )
            }

            is AddEditExpanseEvent.EnteredAmount -> {
                _amount.value = amount.value.copy(
                    text = event.value
                )
            }

            is AddEditExpanseEvent.EnteredCategory -> {
                _category.value = category.value.copy(
                    text = event.value
                )
            }

            is AddEditExpanseEvent.EnteredPaymentMode -> {
                _paymentMode.value = paymentMode.value.copy(
                    text = event.value
                )
            }

            is AddEditExpanseEvent.EnteredTags -> {
                _tags.value = tags.value.copy(
                    tagsList = event.value
                )
            }

            is AddEditExpanseEvent.EnteredNote -> {
                _note.value = note.value.copy(
                    text = event.value
                )
            }

            is AddEditExpanseEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        expanseUseCases.addExpanse(
                            Expanse(
                                date = date.value.text,
                                amount = amount.value.text,
                                category = category.value.text,
                                paymentMode = paymentMode.value.text,
                                tags = tags.value.tagsList,
                                note = note.value.text
                            )
                        )
                    } catch (e: InvalidExpanseException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Couldn't save Expanse"
                            )
                        )
                    }
                }
            }

            else -> {}
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}