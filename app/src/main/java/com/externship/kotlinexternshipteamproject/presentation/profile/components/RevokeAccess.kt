package com.externship.kotlinexternshipteamproject.presentation.profile.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.externship.kotlinexternshipteamproject.domain.model.Response.Failure
import com.externship.kotlinexternshipteamproject.domain.model.Response.Loading
import com.externship.kotlinexternshipteamproject.domain.model.Response.Success
import com.externship.kotlinexternshipteamproject.presentation.components.ProgressBar
import com.externship.kotlinexternshipteamproject.presentation.profile.ProfileViewModel

@Composable
fun RevokeAccess(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToAuthScreen: (accessRevoked: Boolean) -> Unit,
    showSnackBar: () -> Unit
) {
    when (val revokeAccessResponse = viewModel.revokeAccessResponse) {
        is Loading -> ProgressBar()
        is Success -> revokeAccessResponse.data?.let { accessRevoked ->
            LaunchedEffect(accessRevoked) {
                navigateToAuthScreen(accessRevoked)
            }
        }

        is Failure -> LaunchedEffect(Unit) {
            print(revokeAccessResponse.e)
            showSnackBar()
        }
    }
}