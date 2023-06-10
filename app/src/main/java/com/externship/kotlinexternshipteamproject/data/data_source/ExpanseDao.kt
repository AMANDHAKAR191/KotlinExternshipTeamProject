package com.externship.kotlinexternshipteamproject.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.externship.kotlinexternshipteamproject.domain.model.Expanse
import kotlinx.coroutines.flow.Flow


@Dao
interface ExpanseDao {

    @Query("SELECT * FROM expanse")
    fun getExpanses(): Flow<List<Expanse>>

    @Query("SELECT * FROM expanse WHERE id = :id")
    suspend fun getExpanseById(id: Int): Expanse?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inertExpanse(expanse: Expanse)

    @Delete
    suspend fun deleteExpanse(expanse: Expanse)
}