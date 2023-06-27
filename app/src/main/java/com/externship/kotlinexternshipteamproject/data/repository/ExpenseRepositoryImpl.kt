package com.externship.kotlinexternshipteamproject.data.repository

import com.externship.kotlinexternshipteamproject.data.data_source.ExpenseDao
import com.externship.kotlinexternshipteamproject.domain.model.Expense
import com.externship.kotlinexternshipteamproject.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date

class ExpenseRepositoryImpl(
    private val dao: ExpenseDao
) : ExpenseRepository {
    override fun getExpenses(): Flow<List<Expense>> {
        return dao.getExpenses()
    }

    override suspend fun getExpenseById(id: Int): Expense? {
        return dao.getExpenseById(id)
    }

    override fun getSumOfCurrentMonthExpenses(date: Date): Flow<Float?> {
        return dao.getSumOfCurrentMonthExpense(date)
    }

    override suspend fun insertExpense(expense: Expense) {
        dao.inertExpense(expense)
    }

    override suspend fun deleteExpense(expense: Expense) {
        dao.deleteExpense(expense)
    }
}