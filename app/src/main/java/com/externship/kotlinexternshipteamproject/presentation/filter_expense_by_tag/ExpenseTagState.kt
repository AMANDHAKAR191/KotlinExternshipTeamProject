package com.externship.kotlinexternshipteamproject.presentation.filter_expense_by_tag

import com.externship.kotlinexternshipteamproject.domain.model.Expense

data class ExpenseTagState(
    val expense: List<Expense> = emptyList(),
    val expenseTags: List<String> = emptyList()
)