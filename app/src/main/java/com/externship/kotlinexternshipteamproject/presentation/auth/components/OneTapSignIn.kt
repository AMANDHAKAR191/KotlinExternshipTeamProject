package com.externship.kotlinexternshipteamproject.presentation.auth.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.externship.kotlinexternshipteamproject.domain.model.Response.Failure
import com.externship.kotlinexternshipteamproject.domain.model.Response.Loading
import com.externship.kotlinexternshipteamproject.domain.model.Response.Success
import com.externship.kotlinexternshipteamproject.presentation.auth.AuthViewModel
import com.externship.kotlinexternshipteamproject.presentation.components.ProgressBar
import com.google.android.gms.auth.api.identity.BeginSignInResult

@Composable
fun OneTapSignIn(
    viewModel: AuthViewModel = hiltViewModel(),
    launch: (result: BeginSignInResult) -> Unit
) {
    when (val oneTapSignInResponse = viewModel.oneTapSignInResponse) {
        is Loading -> ProgressBar()
        is Success -> oneTapSignInResponse.data?.let {
            LaunchedEffect(it) {
                launch(it)
            }
        }

        is Failure -> LaunchedEffect(Unit) {
            print(oneTapSignInResponse.e)
        }
    }
}