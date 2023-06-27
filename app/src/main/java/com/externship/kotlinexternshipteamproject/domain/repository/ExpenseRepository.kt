package com.externship.kotlinexternshipteamproject.domain.repository

import com.externship.kotlinexternshipteamproject.domain.model.Expense
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ExpenseRepository {

    fun getExpenses(): Flow<List<Expense>>

    suspend fun getExpenseById(id: Int): Expense?

    fun getSumOfCurrentMonthExpenses(date: Date): Flow<Float?>

    suspend fun insertExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)
}