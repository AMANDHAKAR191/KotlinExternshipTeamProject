package com.externship.kotlinexternshipteamproject.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.externship.kotlinexternshipteamproject.R
import com.externship.kotlinexternshipteamproject.core.Constants.SIGN_IN_REQUEST
import com.externship.kotlinexternshipteamproject.core.Constants.SIGN_UP_REQUEST
import com.externship.kotlinexternshipteamproject.data.data_source.ExpanseDatabase
import com.externship.kotlinexternshipteamproject.data.repository.AuthRepositoryImpl
import com.externship.kotlinexternshipteamproject.data.repository.ExpanseRepositoryImpl
import com.externship.kotlinexternshipteamproject.data.repository.ProfileRepositoryImpl
import com.externship.kotlinexternshipteamproject.domain.repository.AuthRepository
import com.externship.kotlinexternshipteamproject.domain.repository.ExpanseRepository
import com.externship.kotlinexternshipteamproject.domain.repository.ProfileRepository
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.AddExpanse
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.DeleteExpanse
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.ExpanseUseCases
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.GetExpanse
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.GetExpanses
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {
    //for authentication
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideOneTapClient(
        @ApplicationContext
        context: Context
    ) = Identity.getSignInClient(context)

    @Provides
    @Named(SIGN_IN_REQUEST)
    fun provideSignInRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    @Provides
    @Named(SIGN_UP_REQUEST)
    fun provideSignUpRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()

    @Provides
    fun provideGoogleSignInOptions(
        app: Application
    ) = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(app.getString(R.string.web_client_id))
        .requestEmail()
        .build()

    @Provides
    fun provideGoogleSignInClient(
        app: Application,
        options: GoogleSignInOptions
    ) = GoogleSignIn.getClient(app, options)

    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        oneTapClient: SignInClient,
        @Named(SIGN_IN_REQUEST)
        signInRequest: BeginSignInRequest,
        @Named(SIGN_UP_REQUEST)
        signUpRequest: BeginSignInRequest,
        db: FirebaseFirestore
    ): AuthRepository = AuthRepositoryImpl(
        auth = auth,
        oneTapClient = oneTapClient,
        signInRequest = signInRequest,
        signUpRequest = signUpRequest,
        db = db
    )

    @Provides
    fun provideProfileRepository(
        auth: FirebaseAuth,
        oneTapClient: SignInClient,
        signInClient: GoogleSignInClient,
        db: FirebaseFirestore
    ): ProfileRepository = ProfileRepositoryImpl(
        auth = auth,
        oneTapClient = oneTapClient,
        signInClient = signInClient,
        db = db
    )

    //for expanse room
    @Provides
    @Singleton
    fun provideExpanseDataBase(app: Application): ExpanseDatabase {
        return Room.databaseBuilder(
            app,
            ExpanseDatabase::class.java,
            ExpanseDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideExpanseRepository(database: ExpanseDatabase): ExpanseRepository {
        return ExpanseRepositoryImpl(database.expanseDao)
    }

    @Provides
    @Singleton
    fun provideExpanseUseCases(repository: ExpanseRepository): ExpanseUseCases {
        return ExpanseUseCases(
            getExpanse = GetExpanse(repository),
            getExpanses = GetExpanses(repository),
            addExpanse = AddExpanse(repository),
            deleteExpanse = DeleteExpanse(repository)
        )
    }
}