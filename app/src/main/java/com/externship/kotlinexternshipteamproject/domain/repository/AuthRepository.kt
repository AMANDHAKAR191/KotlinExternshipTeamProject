package com.externship.kotlinexternshipteamproject.domain.repository

import com.externship.kotlinexternshipteamproject.domain.model.Budget
import com.externship.kotlinexternshipteamproject.domain.model.Response
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow

typealias OneTapSignInResponse = Response<BeginSignInResult>
typealias SignInWithGoogleResponse = Response<Boolean>

interface AuthRepository {
    val isUserAuthenticatedInFirebase: Boolean

    suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse

    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): SignInWithGoogleResponse

    suspend fun saveBudgetAmountInFirebase(budget: Budget): Flow<Boolean>
    suspend fun getBudgetAmountInFirebase(): Flow<Budget>
}