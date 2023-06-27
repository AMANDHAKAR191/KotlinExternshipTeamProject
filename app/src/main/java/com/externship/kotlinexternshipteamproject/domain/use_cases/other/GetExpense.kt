package com.externship.kotlinexternshipteamproject.domain.use_cases.other

import com.externship.kotlinexternshipteamproject.domain.model.Expense
import com.externship.kotlinexternshipteamproject.domain.repository.ExpenseRepository
import javax.inject.Inject

class GetExpense @Inject constructor(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(id: Int): Expense? {
        return repository.getExpenseById(id)
    }
}