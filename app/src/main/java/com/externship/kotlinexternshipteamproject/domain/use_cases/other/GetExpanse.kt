package com.externship.kotlinexternshipteamproject.domain.use_cases.other

import com.externship.kotlinexternshipteamproject.domain.model.Expanse
import com.externship.kotlinexternshipteamproject.domain.repository.ExpanseRepository
import javax.inject.Inject

class GetExpanse @Inject constructor(
    private val repository: ExpanseRepository
) {
    suspend operator fun invoke(id: Int): Expanse? {
        return repository.getExpanseById(id)
    }
}