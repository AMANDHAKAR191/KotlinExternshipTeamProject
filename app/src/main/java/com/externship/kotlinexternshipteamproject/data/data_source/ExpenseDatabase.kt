package com.externship.kotlinexternshipteamproject.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.externship.kotlinexternshipteamproject.domain.model.DateConverter
import com.externship.kotlinexternshipteamproject.domain.model.Expense


@Database(
    entities = [Expense::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract val expenseDao: ExpenseDao

    companion object {
        const val DATABASE_NAME = "expense_database"
    }
}