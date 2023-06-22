package com.externship.kotlinexternshipteamproject.domain.use_cases.other

import com.externship.kotlinexternshipteamproject.domain.repository.ExpanseRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class SumOfCurrentMonthExpanses @Inject constructor(
    private val repository: ExpanseRepository
) {
    suspend operator fun invoke(date: Date): Flow<Float?> {
        return repository.getSumOfCurrentMonthExpanses(date)
    }
}