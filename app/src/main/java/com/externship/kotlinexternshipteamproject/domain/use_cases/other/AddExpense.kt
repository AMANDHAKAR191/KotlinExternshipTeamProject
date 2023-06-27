package com.externship.kotlinexternshipteamproject.domain.use_cases.other

import com.externship.kotlinexternshipteamproject.domain.model.Expense
import com.externship.kotlinexternshipteamproject.domain.model.InvalidExpenseException
import com.externship.kotlinexternshipteamproject.domain.repository.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddExpense @Inject constructor(
    private val repository: ExpenseRepository
) {
    @Throws(InvalidExpenseException::class)
    suspend operator fun invoke(expense: Expense) {
        if (expense.amount == 0) {
            throw InvalidExpenseException("The amount of the expanse can't be empty.")
        }
        withContext(Dispatchers.IO) {
            repository.insertExpense(expense)
        }
    }
}