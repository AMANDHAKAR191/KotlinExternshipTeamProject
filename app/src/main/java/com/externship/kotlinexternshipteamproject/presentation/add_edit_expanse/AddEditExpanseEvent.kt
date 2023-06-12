package com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse

sealed class AddEditExpanseEvent {

    data class EnteredDate(val value: String) : AddEditExpanseEvent()
    data class EnteredAmount(val value: String) : AddEditExpanseEvent()

    data class EnteredCategory(val value: String) : AddEditExpanseEvent()

    data class EnteredPaymentMode(val value: String) : AddEditExpanseEvent()

    data class EnteredTags(val value: String) : AddEditExpanseEvent()

    data class EnteredNote(val value: String) : AddEditExpanseEvent()

    object SaveNote : AddEditExpanseEvent()
}