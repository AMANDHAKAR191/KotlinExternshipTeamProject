package com.externship.kotlinexternshipteamproject.presentation.navigation

import com.externship.kotlinexternshipteamproject.core.Constants.AUTH_SCREEN
import com.externship.kotlinexternshipteamproject.core.Constants.PROFILE_SCREEN

sealed class Screen(val route: String) {
    object AuthScreen : Screen(AUTH_SCREEN)
    object ProfileScreen : Screen(PROFILE_SCREEN)
}