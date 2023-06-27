package com.externship.kotlinexternshipteamproject.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.externship.kotlinexternshipteamproject.domain.model.Expense
import kotlinx.coroutines.flow.Flow
import java.util.Date


@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expense")
    fun getExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM expense WHERE id = :id")
    suspend fun getExpenseById(id: Int): Expense?

    //    @Query("SELECT SUM(amount) AS total_amount FROM EXPANSE WHERE type = 'Expanse' AND strftime(date) = strftime(CURRENT_DATE)")
    @Query("SELECT SUM(amount) as TotalExpanse FROM expense WHERE strftime('%Y-%m', datetime(date / 1000, 'unixepoch')) = strftime('%Y-%m', datetime(:date / 1000, 'unixepoch')) AND type = 'Expanse'")
    fun getSumOfCurrentMonthExpense(date: Date): Flow<Float?>

    @Query("SELECT tags FROM expense")
    fun getAllTags(): Flow<List<String>>

    @Query("SELECT * FROM expense WHERE tags LIKE '%' || :tag || '%'")
    fun getExpenseFilteredByTag(tag: String): Flow<List<Expense>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inertExpense(expense: Expense)

    @Delete
    fun deleteExpense(expense: Expense)
}