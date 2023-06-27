package com.externship.kotlinexternshipteamproject.domain.use_cases.other

import com.externship.kotlinexternshipteamproject.domain.model.Expense
import com.externship.kotlinexternshipteamproject.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetExpensesFilteredByTag @Inject constructor(
    private val repository: ExpenseRepository
) {
    operator fun invoke(tag: String): Flow<List<Expense>> {
        return repository.getExpensesFilteredByTag(tag).map { expenses ->
            expenses.sortedBy { it.date }
        }
    }
}