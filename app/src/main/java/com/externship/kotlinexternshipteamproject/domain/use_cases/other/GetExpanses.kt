package com.externship.kotlinexternshipteamproject.domain.use_cases.other

import com.externship.kotlinexternshipteamproject.domain.model.Expanse
import com.externship.kotlinexternshipteamproject.domain.repository.ExpanseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetExpanses(
    private val repository: ExpanseRepository
) {
    operator fun invoke(): Flow<List<Expanse>> {
        return repository.getExpanses().map { expanses ->
            expanses.sortedBy { it.date }
        }
    }
}