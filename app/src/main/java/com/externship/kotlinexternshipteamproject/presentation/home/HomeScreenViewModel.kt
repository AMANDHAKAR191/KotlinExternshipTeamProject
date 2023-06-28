package com.externship.kotlinexternshipteamproject.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.externship.kotlinexternshipteamproject.domain.repository.ProfileRepository
import com.externship.kotlinexternshipteamproject.domain.use_cases.auth.AuthUseCases
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.ExpenseUseCases
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.AddEditExpenseEvent
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.AddEditExpenseViewModel
import com.externship.kotlinexternshipteamproject.presentation.profile.BudgetTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases,
    private val authUseCases: AuthUseCases,
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _state = mutableStateOf(ExpenseState())
    val state: State<ExpenseState> = _state

    val photoUrl get() = profileRepository.photoUrl
    val displayName get() = profileRepository.displayName

    var _budgetAmount = mutableStateOf(
        BudgetTextFieldState(
            hint = "Budget"
        )
    )
    var budgetAmount: State<BudgetTextFieldState> = _budgetAmount

    private var _sumOfCurrentExpenses = mutableStateOf(ExpenseState())
    var sumOfCurrentExpenses: State<ExpenseState> = _sumOfCurrentExpenses

    private val _eventFlow = MutableSharedFlow<AddEditExpenseViewModel.UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getExpensesJob: Job? = null
    private var getExpensesSumJob: Job? = null

    init {
        getExpensesSum()
        getExpenses()

        viewModelScope.launch {
            authUseCases.getBudgetUseCase.invoke().collect {
                _budgetAmount.value = budgetAmount.value.copy(
                    amount = it.totalBudgetAmount.toString()
                )
                println("budgetAmount1: ${budgetAmount.value.amount}")
            }
        }
        viewModelScope.launch {
            expenseUseCases.getAllTags.invoke().collect {
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


    private fun getExpensesSum() {

        viewModelScope.launch {
            expenseUseCases.sumOfCurrentMonthExpenses.invoke(Date.from(Instant.now())).collect {
                if (it != null) {
                    _sumOfCurrentExpenses.value.expensesSumOfCurrentMonth = it
                }
            }
        }
    }

    fun onEvent(event: AddEditExpenseEvent) {
        when (event) {
            is AddEditExpenseEvent.DeleteExpense -> {
                viewModelScope.launch {
                    expenseUseCases.deleteExpense.invoke(event.expense)
                    _eventFlow.emit(
                        AddEditExpenseViewModel.UiEvent.ShowSnackBar(
                            message = "Expense deleted"
                        )
                    )
                }
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
}