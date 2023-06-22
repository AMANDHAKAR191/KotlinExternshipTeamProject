package com.externship.kotlinexternshipteamproject.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.externship.kotlinexternshipteamproject.domain.model.Expanse
import kotlinx.coroutines.flow.Flow
import java.util.Date


@Dao
interface ExpanseDao {

    @Query("SELECT * FROM expanse")
    fun getExpanses(): Flow<List<Expanse>>

    @Query("SELECT * FROM expanse WHERE id = :id")
    suspend fun getExpanseById(id: Int): Expanse?

    //    @Query("SELECT SUM(amount) AS total_amount FROM EXPANSE WHERE type = 'Expanse' AND strftime(date) = strftime(CURRENT_DATE)")
    @Query("SELECT SUM(amount) as TotalExpanse FROM EXPANSE WHERE strftime('%Y-%m', datetime(date / 1000, 'unixepoch')) = strftime('%Y-%m', datetime(:date / 1000, 'unixepoch')) AND type = 'Expanse'")
    fun getSumOfCurrentMonthExpanse(date: Date): Flow<Float?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inertExpanse(expanse: Expanse)

    @Delete
    suspend fun deleteExpanse(expanse: Expanse)
}