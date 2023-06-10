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
fun SignOut(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToAuthScreen: (signedOut: Boolean) -> Unit
) {
    when (val signOutResponse = viewModel.signOutResponse) {
        is Loading -> ProgressBar()
        is Success -> signOutResponse.data?.let { signedOut ->
            LaunchedEffect(signedOut) {
                navigateToAuthScreen(signedOut)
            }
        }

        is Failure -> LaunchedEffect(Unit) {
            print(signOutResponse.e)
        }
    }
}