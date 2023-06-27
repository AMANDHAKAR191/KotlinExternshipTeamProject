package com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse

import com.externship.kotlinexternshipteamproject.domain.model.Expense

sealed class AddEditExpenseEvent {

    data class ChangeExpenseType(val value: String) : AddEditExpenseEvent()
    data class EnteredDate(val value: String) : AddEditExpenseEvent()
    data class EnteredAmount(val value: String) : AddEditExpenseEvent()

    data class EnteredCategory(val value: String) : AddEditExpenseEvent()

    data class EnteredPaymentMode(val value: String) : AddEditExpenseEvent()

    data class EnteredTags(val value: String) : AddEditExpenseEvent()

    data class EnteredNote(val value: String) : AddEditExpenseEvent()

    object SaveExpense : AddEditExpenseEvent()

    data class GetExpensesFilteredByTag(val tag: String) : AddEditExpenseEvent()
    data class DeleteExpense(val expense: Expense) : AddEditExpenseEvent()
}