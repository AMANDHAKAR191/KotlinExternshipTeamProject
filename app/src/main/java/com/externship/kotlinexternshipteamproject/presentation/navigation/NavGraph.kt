package com.externship.kotlinexternshipteamproject.presentation.navigation

import android.app.Activity
import android.content.Context
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.AddEditExpanseScreen
import com.externship.kotlinexternshipteamproject.presentation.auth.AuthScreen
import com.externship.kotlinexternshipteamproject.presentation.home.HomeScreen
import com.externship.kotlinexternshipteamproject.presentation.navigation.Screen.AuthScreen
import com.externship.kotlinexternshipteamproject.presentation.navigation.Screen.ProfileScreen
import com.externship.kotlinexternshipteamproject.presentation.profile.ProfileScreen

@Composable
@ExperimentalAnimationApi
fun NavGraph(
    navController: NavHostController,
    context: Context, activity: Activity
) {
    NavHost(
        navController = navController,
        startDestination = AuthScreen.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(
            route = AuthScreen.route
        ) {
            AuthScreen(
                navigateToProfileScreen = {
                    navController.popBackStack()
                    navController.navigate(Screen.HomeScreen.route)
                }
            )
        }
        composable(
            route = ProfileScreen.route
        ) {
            ProfileScreen(
                navigateToAuthScreen = {
                    navController.popBackStack()
                    navController.navigate(AuthScreen.route)
                },
                navigateToHomeScreen = {
                    navController.popBackStack()
                    navController.navigate(Screen.HomeScreen.route)
                }
            )
        }
        composable(route = Screen.AddEditExpanseScreen.route) {
            AddEditExpanseScreen(navigateToHomeScreen = {
                navController.popBackStack()
                navController.navigate(Screen.HomeScreen.route)
            }, context = context, activity = activity)
        }
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navigateToAddEditExpanseScreen = {
                navController.navigate(Screen.AddEditExpanseScreen.route)
            }, navigateToProfileScreen = {
                navController.navigate(ProfileScreen.route)
            })
        }
    }
}