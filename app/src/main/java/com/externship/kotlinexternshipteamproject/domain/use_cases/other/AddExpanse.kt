package com.externship.kotlinexternshipteamproject.domain.use_cases.other

import com.externship.kotlinexternshipteamproject.domain.model.Expanse
import com.externship.kotlinexternshipteamproject.domain.model.InvalidExpanseException
import com.externship.kotlinexternshipteamproject.domain.repository.ExpanseRepository
import javax.inject.Inject

class AddExpanse @Inject constructor(
    private val repository: ExpanseRepository
) {
    @Throws(InvalidExpanseException::class)
    suspend operator fun invoke(expanse: Expanse) {
        if (expanse.date.isBlank()) {
            throw InvalidExpanseException("The date of the expanse can't be empty.")
        }
        if (expanse.amount.isBlank()) {
            throw InvalidExpanseException("The amount of the expanse can't be empty.")
        }
        repository.insertExpanse(expanse)
    }
}