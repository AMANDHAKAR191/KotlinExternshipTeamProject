package com.externship.kotlinexternshipteamproject.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.externship.kotlinexternshipteamproject.domain.repository.ProfileRepository
import com.externship.kotlinexternshipteamproject.domain.use_cases.auth.AuthUseCases
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.ExpanseUseCases
import com.externship.kotlinexternshipteamproject.presentation.profile.BudgetTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val expanseUseCases: ExpanseUseCases,
    private val authUseCases: AuthUseCases,
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _state = mutableStateOf(ExpanseState())
    val state: State<ExpanseState> = _state

    val photoUrl get() = profileRepository.photoUrl

    var _budgetAmount = mutableStateOf(
        BudgetTextFieldState(
            hint = "Budget"
        )
    )
    var budgetAmount: State<BudgetTextFieldState> = _budgetAmount

    private var _sumOfCurrentExpanses = mutableStateOf(ExpanseState())
    var sumOfCurrentExpanses: State<ExpanseState> = _sumOfCurrentExpanses

    private var getExpansesJob: Job? = null
    private var getExpansesSumJob: Job? = null

    init {
        getExpansesSum()
        getExpanses()

        viewModelScope.launch {
            authUseCases.getBudgetUseCase.invoke().collect {
                _budgetAmount.value = budgetAmount.value.copy(
                    amount = it.totalBudgetAmount.toString()
                )
                println("budgetAmount1: ${budgetAmount.value.amount}")
            }
        }
    }

    private fun getExpansesSum() {

        viewModelScope.launch {
            expanseUseCases.sumOfCurrentMonthExpanses.invoke(Date.from(Instant.now())).collect {
                if (it != null) {
                    _sumOfCurrentExpanses.value.expansesSumOfCurrentMonth = it
                }
            }
        }
    }

    fun onEvent(event: ExpanseEvent) {
        //implement later
    }


    private fun getExpanses() {
        getExpansesJob?.cancel()
        getExpansesJob = expanseUseCases.getExpanses()
            .onEach { expanses ->
                _state.value = state.value.copy(
                    expanses = expanses
                )
            }.launchIn(viewModelScope)
    }
}