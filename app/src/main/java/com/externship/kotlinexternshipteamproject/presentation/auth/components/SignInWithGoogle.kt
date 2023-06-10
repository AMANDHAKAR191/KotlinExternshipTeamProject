package com.externship.kotlinexternshipteamproject.presentation.auth.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.externship.kotlinexternshipteamproject.domain.model.Response.Failure
import com.externship.kotlinexternshipteamproject.domain.model.Response.Loading
import com.externship.kotlinexternshipteamproject.domain.model.Response.Success
import com.externship.kotlinexternshipteamproject.presentation.auth.AuthViewModel
import com.externship.kotlinexternshipteamproject.presentation.components.ProgressBar

@Composable
fun SignInWithGoogle(
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToHomeScreen: (signedIn: Boolean) -> Unit
) {
    when (val signInWithGoogleResponse = viewModel.signInWithGoogleResponse) {
        is Loading -> ProgressBar()
        is Success -> signInWithGoogleResponse.data?.let { signedIn ->
            LaunchedEffect(signedIn) {
                navigateToHomeScreen(signedIn)
            }
        }

        is Failure -> LaunchedEffect(Unit) {
            print(signInWithGoogleResponse.e)
        }
    }
}