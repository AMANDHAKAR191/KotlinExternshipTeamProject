package com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse

import androidx.compose.ui.focus.FocusState

sealed class AddEditExpanseEvent {
    data class EnteredAmount(val value: String) : AddEditExpanseEvent()

    data class ChangeAmountFocus(val focusState: FocusState) : AddEditExpanseEvent()

    data class EnteredNote(val value: String) : AddEditExpanseEvent()

    data class ChangeNoteFocus(val focusState: FocusState) : AddEditExpanseEvent()

    data class EnteredTag(val value: String) : AddEditExpanseEvent()

    data class ChangeTagsFocus(val focusState: FocusState) : AddEditExpanseEvent()

    object SaveNote : AddEditExpanseEvent()
}