package com.externship.kotlinexternshipteamproject.domain.use_cases.other

import com.externship.kotlinexternshipteamproject.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTags @Inject constructor(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<String>> {
        return repository.getAllTags()
    }
}