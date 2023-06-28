package com.externship.kotlinexternshipteamproject.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.externship.kotlinexternshipteamproject.presentation.auth.AuthViewModel
import com.externship.kotlinexternshipteamproject.presentation.navigation.NavGraph
import com.externship.kotlinexternshipteamproject.presentation.navigation.Screen
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val viewModel by viewModels<AuthViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            navController = rememberNavController()
            Firebase.database.setPersistenceEnabled(true)
            NavGraph(
                navController = navController,
                context = context, activity = this@MainActivity
            )
            checkAuthState()
        }
        onBackPressedDispatcher.addCallback(this) {
            if (!navController.navigateUp()) {
                finish()
            }
        }
    }

    private fun checkAuthState() {
        if (viewModel.isUserAuthenticated) {
            navigateToProfileScreen()
        }
    }

    private fun navigateToProfileScreen() {
        navController.popBackStack()
        navController.navigate(Screen.HomeScreen.route)
    }

}