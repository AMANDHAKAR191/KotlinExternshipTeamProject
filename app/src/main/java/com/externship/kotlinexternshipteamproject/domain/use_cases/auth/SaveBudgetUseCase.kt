package com.externship.kotlinexternshipteamproject.domain.use_cases.auth

import com.externship.kotlinexternshipteamproject.domain.model.Budget
import com.externship.kotlinexternshipteamproject.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveBudgetUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(budget: Budget): Flow<Boolean> {
        return repository.saveBudgetAmountInFirebase(budget)
    }
}