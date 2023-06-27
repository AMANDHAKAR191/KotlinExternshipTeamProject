package com.externship.kotlinexternshipteamproject.domain.use_cases.other

import com.externship.kotlinexternshipteamproject.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class SumOfCurrentMonthExpenses @Inject constructor(
    private val repository: ExpenseRepository
) {
    operator fun invoke(date: Date): Flow<Float?> {
        return repository.getSumOfCurrentMonthExpenses(date)
    }
}