package com.externship.kotlinexternshipteamproject.presentation.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.externship.kotlinexternshipteamproject.core.Constants.EXIT_DURATION
import com.externship.kotlinexternshipteamproject.core.Constants.REVOKE_ACCESS_MESSAGE
import com.externship.kotlinexternshipteamproject.core.Constants.SIGN_OUT
import com.externship.kotlinexternshipteamproject.presentation.navigation.EnterAnimationForProfileScreen
import com.externship.kotlinexternshipteamproject.presentation.profile.components.ProfileContent
import com.externship.kotlinexternshipteamproject.presentation.profile.components.ProfileTopBar
import com.externship.kotlinexternshipteamproject.presentation.profile.components.RevokeAccess
import com.externship.kotlinexternshipteamproject.presentation.profile.components.SignOut
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToAuthScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()

    var isVisible by remember { mutableStateOf(true) }
    // Define a separate lambda for handling back navigation
    val handleBackNavigation: () -> Unit = {
        isVisible = false
        coroutineScope.launch {
            delay(EXIT_DURATION.toLong()) // Adjust this to match your animation duration
            navigateToHomeScreen()
        }
    }

    EnterAnimationForProfileScreen(
        visible = isVisible
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            topBar = {
                ProfileTopBar(
                    signOut = {
                        viewModel.signOut()
                    },
                    revokeAccess = {
                        viewModel.revokeAccess()
                    },
                    navigateBack = {
                        handleBackNavigation()
                    }
                )
            },
            content = { padding ->
                println("viewModel.isLoading.value.isLoadingVisible1: ${viewModel.isLoading.value.isLoadingVisible}")
                ProfileContent(
                    padding = padding,
                    photoUrl = viewModel.photoUrl,
                    displayName = viewModel.displayName,
                    budgetAmount = viewModel.budgetAmount.value.amount.toFloat(),
                    isLoadingVisible = viewModel.isLoading.value.isLoadingVisible,
                    onBudgetAmountChanged = {
                        viewModel.onEvent(AddEditBudgetEvent.ChangeBudgetAmount(it))
                    },
                    onTrailingIconClicked = {
                        viewModel.onEvent(AddEditBudgetEvent.SaveBudget)
                    }
                )
            }
        )
    }


    SignOut(
        navigateToAuthScreen = { signedOut ->
            if (signedOut) {
                navigateToAuthScreen()
            }
        }
    )

    fun showSnackBar() = coroutineScope.launch {
        val result = snackBarHostState.showSnackbar(
            message = REVOKE_ACCESS_MESSAGE,
            actionLabel = SIGN_OUT
        )
        if (result == SnackbarResult.ActionPerformed) {
            viewModel.signOut()
        }
    }

    RevokeAccess(
        navigateToAuthScreen = { accessRevoked ->
            if (accessRevoked) {
                navigateToAuthScreen()
            }
        },
        showSnackBar = {
            showSnackBar()
        }
    )
}