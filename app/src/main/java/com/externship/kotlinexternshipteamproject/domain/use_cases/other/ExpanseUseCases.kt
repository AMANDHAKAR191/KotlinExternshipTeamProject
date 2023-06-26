package com.externship.kotlinexternshipteamproject.domain.use_cases.other


data class ExpanseUseCases(
    val getExpanses: GetExpanses,
    val getExpanse: GetExpanse,
    val sumOfCurrentMonthExpanses: SumOfCurrentMonthExpanses,
    val addExpanse: AddExpanse,
    val deleteExpanse: DeleteExpanse
)
