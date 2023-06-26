package com.externship.kotlinexternshipteamproject.presentation.profile

data class BudgetTextFieldState(
    var amount: String = "${Float.MAX_VALUE}",
    val hint: String = "",
    var isLoadingVisible: Boolean = true
)