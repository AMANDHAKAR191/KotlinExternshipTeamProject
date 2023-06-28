package com.externship.kotlinexternshipteamproject.presentation.all_expense_screen

import com.externship.kotlinexternshipteamproject.domain.model.Expense

data class ExpenseState(
    val expense: List<Expense> = emptyList(),
    var expensesSumOfCurrentMonth: Float = 0f
)