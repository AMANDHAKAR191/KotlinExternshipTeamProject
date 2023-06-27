package com.externship.kotlinexternshipteamproject.presentation.navigation

import com.externship.kotlinexternshipteamproject.core.Constants.ADD_EDIT_EXPENSE_SCREEN
import com.externship.kotlinexternshipteamproject.core.Constants.AUTH_SCREEN
import com.externship.kotlinexternshipteamproject.core.Constants.HOME_SCREEN
import com.externship.kotlinexternshipteamproject.core.Constants.PROFILE_SCREEN

sealed class Screen(val route: String) {
    object AuthScreen : Screen(AUTH_SCREEN)
    object ProfileScreen : Screen(PROFILE_SCREEN)
    object AddEditExpenseScreen : Screen(ADD_EDIT_EXPENSE_SCREEN)
    object HomeScreen : Screen(HOME_SCREEN)
}