package com.externship.kotlinexternshipteamproject.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.externship.kotlinexternshipteamproject.domain.model.Expanse

@Database(
    entities = [Expanse::class],
    version = 1
)
abstract class ExpanseDatabase : RoomDatabase() {
    abstract val expanseDao: ExpanseDao

    companion object {
        const val DATABASE_NAME = "expanse_db"
    }
}