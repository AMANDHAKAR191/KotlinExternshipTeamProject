package com.externship.kotlinexternshipteamproject.presentation.navigation

import android.app.Activity
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.externship.kotlinexternshipteamproject.core.Constants.ENTER_DURATION
import com.externship.kotlinexternshipteamproject.core.Constants.EXIT_DURATION
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.AddEditExpanseScreen
import com.externship.kotlinexternshipteamproject.presentation.all_expense_screen.AllExpenseScreen
import com.externship.kotlinexternshipteamproject.presentation.auth.AuthScreen
import com.externship.kotlinexternshipteamproject.presentation.filter_expense_by_tag.FilterByTagScreen
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
        composable(route = AuthScreen.route) {
            AuthScreen(
                navigateToProfileScreen = {
                    navController.popBackStack()
                    navController.navigate(Screen.HomeScreen.route)
                }
            )
        }
        composable(route = ProfileScreen.route) {
            ProfileScreen(
                navigateToAuthScreen = {
                    navController.popBackStack()
                    navController.navigate(AuthScreen.route)
                },
                navigateToHomeScreen = {
//                    navController.popBackStack()
                    navController.navigate(Screen.HomeScreen.route)
                }
            )
        }
        composable(route = Screen.AddEditExpenseScreen.route) {
            AddEditExpanseScreen(navigateToHomeScreen = {
                navController.popBackStack()
                navController.navigate(Screen.HomeScreen.route)
            }, context = context, activity = activity)
        }
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                navigateToAddEditExpanseScreen = {
                    navController.navigate(Screen.AddEditExpenseScreen.route)
                },
                navigateToProfileScreen = {
                    navController.navigate(ProfileScreen.route)
                },
                navigateToFilterByTagScreen = {
                    navController.navigate(Screen.FilterByTagScreen.route)
                },
                navigateToAllExpenseScreen = {
                    navController.navigate(Screen.AllExpenseScreen.route)
                }
            )
        }
        composable(route = Screen.FilterByTagScreen.route) {
            FilterByTagScreen(navigateToHomeScreen = {
                navController.popBackStack()
                navController.navigate(Screen.HomeScreen.route)
            })
        }
        composable(route = Screen.AllExpenseScreen.route) {
            AllExpenseScreen(navigateToHomeScreen = {
                navController.popBackStack()
                navController.navigate(Screen.HomeScreen.route)
            },
                navigateToFilterByTagScreen = {
                    navController.navigate(Screen.HomeScreen.route)
                })
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EnterAnimation(visible: Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = slideIn(
            initialOffset = { IntOffset(1000, 0) },  // slide in from right
            animationSpec = tween(ENTER_DURATION, easing = FastOutSlowInEasing),
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOut(
            targetOffset = { IntOffset(1000, 0) },  // slide out to right
            animationSpec = tween(EXIT_DURATION, easing = FastOutSlowInEasing),
        ) + fadeOut(),
        content = content,
        initiallyVisible = visible
    )
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EnterAnimationForProfileScreen(visible: Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + scaleIn(
            initialScale = 0.3f,
            animationSpec = tween(ENTER_DURATION, easing = FastOutSlowInEasing),
            transformOrigin = TransformOrigin(1f, 0f)
        ),
        exit = fadeOut() + scaleOut(
            targetScale = 0.3f,
            animationSpec = tween(EXIT_DURATION, easing = FastOutSlowInEasing),
            transformOrigin = TransformOrigin(1f, 0f)
        ),
        content = content,
        initiallyVisible = false
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EnterAnimationForFAB(visible: Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + scaleIn(
            initialScale = 0.3f,
            animationSpec = tween(ENTER_DURATION, easing = FastOutSlowInEasing),
            transformOrigin = TransformOrigin(1f, 1f)
        ),
        exit = fadeOut() + scaleOut(
            targetScale = 0.3f,
            animationSpec = tween(EXIT_DURATION, easing = FastOutSlowInEasing),
            transformOrigin = TransformOrigin(1f, 1f)
        ),
        content = content,
        initiallyVisible = false
    )
}
