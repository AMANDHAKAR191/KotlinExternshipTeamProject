package com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse

import androidx.room.TypeConverter

class ListStringConverter {
    @TypeConverter
    fun fromListString(value: List<String>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toListString(value: String): List<String> {
        return value.split(",")
    }
}
