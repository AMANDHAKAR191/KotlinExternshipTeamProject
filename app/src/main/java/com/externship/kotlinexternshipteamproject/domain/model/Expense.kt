package com.externship.kotlinexternshipteamproject.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.Instant
import java.util.Date

@Entity()
data class Expense(
    val date: Date = Date.from(Instant.now()),
    val amount: Int,
    val category: String,
    val paymentMode: String,
    val tags: String,
    val note: String,
    val type: String,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val expanseType = listOf("Expense", "Income")
    }
}

class InvalidExpenseException(message: String) : Exception(message)

object DateConverter {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong == null) null else Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}
