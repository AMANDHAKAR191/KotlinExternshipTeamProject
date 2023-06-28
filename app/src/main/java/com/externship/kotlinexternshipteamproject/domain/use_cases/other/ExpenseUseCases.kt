package com.externship.kotlinexternshipteamproject.domain.use_cases.other


data class ExpenseUseCases(
    val getExpenses: GetExpenses,
    val getExpense: GetExpense,
    val sumOfCurrentMonthExpenses: SumOfCurrentMonthExpenses,
    val getAllTags: GetAllTags,
    val getExpensesFilteredByTag: GetExpensesFilteredByTag,
    val getExpensesFilteredByType: GetExpensesFilteredByType,
    val addExpense: AddExpense,
    val deleteExpense: DeleteExpense
)
