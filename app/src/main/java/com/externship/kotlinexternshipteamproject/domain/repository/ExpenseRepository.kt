package com.externship.kotlinexternshipteamproject.domain.repository

import com.externship.kotlinexternshipteamproject.domain.model.Expense
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ExpenseRepository {

    fun getExpenses(): Flow<List<Expense>>

    suspend fun getExpenseById(id: Int): Expense?

    fun getSumOfCurrentMonthExpenses(date: Date): Flow<Float?>

    fun getAllTags(): Flow<List<String>>

    fun getExpensesFilteredByTag(tag: String): Flow<List<Expense>>
    fun getExpensesFilteredByType(expenseType: String): Flow<List<Expense>>

    suspend fun insertExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)
}