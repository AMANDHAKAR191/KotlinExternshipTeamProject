package com.externship.kotlinexternshipteamproject.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.externship.kotlinexternshipteamproject.R
import com.externship.kotlinexternshipteamproject.core.Constants.SIGN_IN_REQUEST
import com.externship.kotlinexternshipteamproject.core.Constants.SIGN_UP_REQUEST
import com.externship.kotlinexternshipteamproject.data.data_source.ExpenseDatabase
import com.externship.kotlinexternshipteamproject.data.repository.AuthRepositoryImpl
import com.externship.kotlinexternshipteamproject.data.repository.ExpenseRepositoryImpl
import com.externship.kotlinexternshipteamproject.data.repository.ProfileRepositoryImpl
import com.externship.kotlinexternshipteamproject.domain.repository.AuthRepository
import com.externship.kotlinexternshipteamproject.domain.repository.ExpenseRepository
import com.externship.kotlinexternshipteamproject.domain.repository.ProfileRepository
import com.externship.kotlinexternshipteamproject.domain.use_cases.auth.AuthUseCases
import com.externship.kotlinexternshipteamproject.domain.use_cases.auth.GetBudgetUseCase
import com.externship.kotlinexternshipteamproject.domain.use_cases.auth.SaveBudgetUseCase
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.AddExpense
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.DeleteExpense
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.ExpenseUseCases
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.GetExpense
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.GetExpenses
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.SumOfCurrentMonthExpenses
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {
    //for authentication
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideFirebaseDatabase() = Firebase.database

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
        db: FirebaseDatabase
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
        db: FirebaseDatabase
    ): ProfileRepository = ProfileRepositoryImpl(
        auth = auth,
        oneTapClient = oneTapClient,
        signInClient = signInClient,
        db = db
    )

    //for expanse room
    @Provides
    fun provideExpenseDataBase(app: Application): ExpenseDatabase {
        return Room.databaseBuilder(
            app,
            ExpenseDatabase::class.java,
            ExpenseDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideExpenseRepository(database: ExpenseDatabase): ExpenseRepository {
        return ExpenseRepositoryImpl(database.expenseDao)
    }

    @Provides
    fun provideExpenseUseCases(repository: ExpenseRepository): ExpenseUseCases {
        return ExpenseUseCases(
            getExpense = GetExpense(repository),
            getExpenses = GetExpenses(repository),
            sumOfCurrentMonthExpenses = SumOfCurrentMonthExpenses(repository),
            addExpense = AddExpense(repository),
            deleteExpense = DeleteExpense(repository)
        )
    }

    @Provides
    fun provideAuthUseCases(repository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            getBudgetUseCase = GetBudgetUseCase(repository),
            saveBudgetUseCase = SaveBudgetUseCase(repository)
        )
    }
}