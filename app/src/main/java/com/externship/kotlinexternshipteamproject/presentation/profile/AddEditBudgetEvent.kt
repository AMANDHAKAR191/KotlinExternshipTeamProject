package com.externship.kotlinexternshipteamproject.presentation.profile

sealed class AddEditBudgetEvent {

    data class ChangeBudgetAmount(val value: String) : AddEditBudgetEvent()

    object SaveBudget : AddEditBudgetEvent()
}