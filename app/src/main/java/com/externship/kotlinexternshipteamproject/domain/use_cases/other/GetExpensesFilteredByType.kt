package com.externship.kotlinexternshipteamproject.domain.use_cases.other

import com.externship.kotlinexternshipteamproject.domain.model.Expense
import com.externship.kotlinexternshipteamproject.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetExpensesFilteredByType @Inject constructor(
    private val repository: ExpenseRepository
) {
    operator fun invoke(expenseType: String): Flow<List<Expense>> {
        return repository.getExpensesFilteredByType(expenseType).map { expenses ->
            expenses.sortedByDescending { it.date }
        }
    }
}