package com.externship.kotlinexternshipteamproject.data.repository

import com.externship.kotlinexternshipteamproject.core.Constants.SIGN_IN_REQUEST
import com.externship.kotlinexternshipteamproject.core.Constants.SIGN_UP_REQUEST
import com.externship.kotlinexternshipteamproject.core.Constants.USERS
import com.externship.kotlinexternshipteamproject.domain.model.Budget
import com.externship.kotlinexternshipteamproject.domain.model.Response
import com.externship.kotlinexternshipteamproject.domain.model.User
import com.externship.kotlinexternshipteamproject.domain.repository.AuthRepository
import com.externship.kotlinexternshipteamproject.domain.repository.OneTapSignInResponse
import com.externship.kotlinexternshipteamproject.domain.repository.SignInWithGoogleResponse
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FieldValue.serverTimestamp
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    @Named(SIGN_IN_REQUEST)
    private var signInRequest: BeginSignInRequest,
    @Named(SIGN_UP_REQUEST)
    private var signUpRequest: BeginSignInRequest,
    private val db: FirebaseDatabase
) : AuthRepository {
    override val isUserAuthenticatedInFirebase = auth.currentUser != null

    override suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse {
        return try {
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
            Response.Success(signInResult)
        } catch (e: Exception) {
            try {
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                Response.Success(signUpResult)
            } catch (e: Exception) {
                Response.Failure(e)
            }
        }
    }

    override suspend fun firebaseSignInWithGoogle(
        googleCredential: AuthCredential
    ): SignInWithGoogleResponse {
        return try {
            val authResult = auth.signInWithCredential(googleCredential).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
            if (isNewUser) {
                addUserToFireStore()
            }
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    private suspend fun addUserToFireStore() {
        auth.currentUser?.apply {
            val user = User(
                displayName,
                email,
                photoUrl?.toString(),
                serverTimestamp().toString()
            )
            db.reference.child(USERS).child(uid).setValue(user).await()
        }
    }

    override suspend fun saveBudgetAmountInFirebase(budget: Budget): Flow<Boolean> =
        callbackFlow {
            auth.currentUser?.apply {
                db.reference.child("budgetAmount").child(uid).setValue(budget)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            trySend(true)  // Emit the result to the flow
                        } else {
                            trySend(false) // Emit the result to the flow
                        }
                    }.addOnFailureListener {
                        trySend(false)    // Emit the result to the flow
                    }.await()
            }
            awaitClose()
        }


    override suspend fun getBudgetAmountInFirebase(): Flow<Budget> = callbackFlow {
        auth.currentUser?.apply {
            val databaseRef = db.reference.child("budgetAmount").child(uid)
            databaseRef.keepSynced(true)
            databaseRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val dataSnapshot = task.result
                    println("dataSnapshot: ${dataSnapshot.value}")
                    if (dataSnapshot != null && dataSnapshot.exists()) {
                        val budgetAmount = dataSnapshot.getValue(Budget::class.java)
                        println("budgetAmount: $budgetAmount")
                        if (budgetAmount != null) {
                            trySend(budgetAmount)
                        }
                    } else {
                        close(FirebaseException("User not found"))
                    }
                } else {
                    close(task.exception ?: FirebaseException("Failed to fetch budget amount"))
                }
            }
        }
        awaitClose()
    }

}