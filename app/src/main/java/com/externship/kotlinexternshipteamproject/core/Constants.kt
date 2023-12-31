package com.externship.kotlinexternshipteamproject.core

object Constants {
    //App
    const val TAG = "AppTag"

    //Collection References
    const val USERS = "users"

    //User fields
    const val DISPLAY_NAME = "displayName"
    const val EMAIL = "email"
    const val PHOTO_URL = "photoUrl"
    const val CREATED_AT = "createdAt"

    //Names
    const val SIGN_IN_REQUEST = "signInRequest"
    const val SIGN_UP_REQUEST = "signUpRequest"

    //Buttons
    const val SIGN_IN_WITH_GOOGLE = "Sign in with Google"
    const val SIGN_OUT = "Sign-out"
    const val REVOKE_ACCESS = "Revoke Access"

    //
    const val EXPENSE = "Expense"
    const val INCOME = "Income"

    //
    const val ENTER_DURATION = 500
    const val EXIT_DURATION = 200

    //Screens
    const val AUTH_SCREEN = "Authentication"
    const val PROFILE_SCREEN = "Profile"
    const val ADD_EDIT_EXPENSE_SCREEN = "AddEditExpanse"
    const val HOME_SCREEN = "HomeScreen"
    const val FILTER_BY_TAG_SCREEN = "FilterByTagScreen"
    const val ALL_EXPENSE_SCREEN = "AllExpenseScreen"

    //Messages
    const val REVOKE_ACCESS_MESSAGE = "You need to re-authenticate before revoking the access."
}