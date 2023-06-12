package com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse

data class ExpanseTextFieldState(
    var text: String = "",
    var tagsList: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)