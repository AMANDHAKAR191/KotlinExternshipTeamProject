package com.externship.kotlinexternshipteamproject.presentation.all_expense_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.ExpenseUseCases
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.AddEditExpenseEvent
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.AddEditExpenseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AllExpenseScreenViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases
) : ViewModel() {
    private val _state = mutableStateOf(ExpenseState())
    val state: State<ExpenseState> = _state

    private val _eventFlow = MutableSharedFlow<AddEditExpenseViewModel.UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getExpensesJob: Job? = null

    init {
        getExpenses()
    }

    fun onEvent(event: AddEditExpenseEvent) {
        when (event) {
            is AddEditExpenseEvent.GetExpensesFilteredByType -> {
                getExpensesByType(event.expenseType)
            }

            else -> {}
        }
    }

    private fun getExpenses() {
        getExpensesJob?.cancel()
        getExpensesJob = expenseUseCases.getExpenses()
            .onEach { expanses ->
                _state.value = state.value.copy(
                    expense = expanses
                )
            }.launchIn(viewModelScope)
    }

    private fun getExpensesByType(expenseType: String) {
        getExpensesJob?.cancel()
        getExpensesJob = expenseUseCases.getExpensesFilteredByType(expenseType)
            .onEach { expense ->
                _state.value = state.value.copy(
                    expense = expense
                )
                println("Expense: $expense")
            }.launchIn(viewModelScope)
    }
}