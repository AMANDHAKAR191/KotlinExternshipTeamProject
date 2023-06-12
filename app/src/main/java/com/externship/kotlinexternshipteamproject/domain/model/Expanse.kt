package com.externship.kotlinexternshipteamproject.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Expanse(
    val date: String,
    val amount: String,
    val category: String,
    val paymentMode: String,
    val tags: String,
    val note: String,
    @PrimaryKey val id: Int? = null
)

class InvalidExpanseException(message: String) : Exception(message)