package com.externship.kotlinexternshipteamproject.data.repository

import com.externship.kotlinexternshipteamproject.data.data_source.ExpanseDao
import com.externship.kotlinexternshipteamproject.domain.model.Expanse
import com.externship.kotlinexternshipteamproject.domain.repository.ExpanseRepository
import kotlinx.coroutines.flow.Flow

class ExpanseRepositoryImpl(
    private val dao: ExpanseDao
) : ExpanseRepository {
    override fun getNotes(): Flow<List<Expanse>> {
        return dao.getExpanses()
    }

    override suspend fun getExpanseById(id: Int): Expanse? {
        return dao.getExpanseById(id)
    }

    override suspend fun insertExpanse(expanse: Expanse) {
        return dao.inertExpanse(expanse)
    }

    override suspend fun deleteExpanse(expanse: Expanse) {
        return dao.deleteExpanse(expanse)
    }
}