package com.externship.kotlinexternshipteamproject.domain.use_cases.other


data class ExpenseUseCases(
    val getExpenses: GetExpenses,
    val getExpense: GetExpense,
    val sumOfCurrentMonthExpenses: SumOfCurrentMonthExpenses,
    val addExpense: AddExpense,
    val deleteExpense: DeleteExpense
)