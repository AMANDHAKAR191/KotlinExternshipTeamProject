package com.externship.kotlinexternshipteamproject.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.ExpanseUseCases
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
    private val expanseUseCases: ExpanseUseCases
) : ViewModel() {
    private val _state = mutableStateOf(ExpanseState())
    val state: State<ExpanseState> = _state

    private var _sumOfCurrentExpanses = mutableStateOf(ExpanseState())
    var sumOfCurrentExpanses: State<ExpanseState> = _sumOfCurrentExpanses

    private var getExpansesJob: Job? = null
    private var getExpansesSumJob: Job? = null

    init {
        getExpansesSum()
        getExpanses()
    }

    private fun getExpansesSum() {

        viewModelScope.launch {
            expanseUseCases.sumOfCurrentMonthExpanses.invoke(Date.from(Instant.now())).collect {
                if (it != null) {
                    _sumOfCurrentExpanses.value.expansesSumOfCurrentMonth = it
                    println("total expanse $it")
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