package com.externship.kotlinexternshipteamproject.domain.use_cases.auth

import com.externship.kotlinexternshipteamproject.domain.model.Budget
import com.externship.kotlinexternshipteamproject.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBudgetUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Flow<Budget> {
        return repository.getBudgetAmountInFirebase()
    }
}