package com.externship.kotlinexternshipteamproject.presentation.filter_expense_by_tag

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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterByTagViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases,
) : ViewModel() {
    private val _state = mutableStateOf(ExpenseTagState())
    val state: State<ExpenseTagState> = _state

    private val _eventFlow = MutableSharedFlow<AddEditExpenseViewModel.UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getExpensesJob: Job? = null
    private var getExpensesSumJob: Job? = null

    init {
        viewModelScope.launch {
            expenseUseCases.getAllTags.invoke().collect {
                _state.value = state.value.copy(
                    expenseTags = getUniqueTags(it)
                )
                getUniqueTags(it).forEach {
                    println("tags: $it")
                }
            }
        }
    }

    private fun getUniqueTags(tagList: List<String>): List<String> {
        val uniqueTags = mutableListOf<String>()
        for (tagString in tagList) {
            val tags = tagString.split(",").map { it.trim() }
            uniqueTags.addAll(tags)
        }
        return uniqueTags.distinct()
    }


    fun onEvent(event: AddEditExpenseEvent) {
        when (event) {
            is AddEditExpenseEvent.GetExpensesFilteredByTag -> {
                getExpenses(event.tag)
            }

            else -> {}
        }
    }

    private fun getExpenses(tag: String) {
        getExpensesJob?.cancel()
        getExpensesJob = expenseUseCases.getExpensesFilteredByTag(tag)
            .onEach { expanses ->
                _state.value = state.value.copy(
                    expense = expanses
                )
            }.launchIn(viewModelScope)
    }
}