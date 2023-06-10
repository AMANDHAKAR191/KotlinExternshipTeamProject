package com.externship.kotlinexternshipteamproject.domain.repository

import com.externship.kotlinexternshipteamproject.domain.model.Expanse
import kotlinx.coroutines.flow.Flow

interface ExpanseRepository {

    fun getNotes(): Flow<List<Expanse>>

    suspend fun getExpanseById(id: Int): Expanse?

    suspend fun insertExpanse(expanse: Expanse)

    suspend fun deleteExpanse(expanse: Expanse)
}