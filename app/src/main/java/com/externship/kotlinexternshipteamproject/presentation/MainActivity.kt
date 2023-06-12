package com.externship.kotlinexternshipteamproject.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.externship.kotlinexternshipteamproject.presentation.auth.AuthViewModel
import com.externship.kotlinexternshipteamproject.presentation.navigation.NavGraph
import com.externship.kotlinexternshipteamproject.presentation.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            NavGraph(
                navController = navController
            )
            checkAuthState()
        }
    }

    private fun checkAuthState() {
        if (viewModel.isUserAuthenticated) {
            navigateToProfileScreen()
        }
    }

    private fun navigateToProfileScreen() =
        navController.navigate(Screen.HomeScreen.route)
}