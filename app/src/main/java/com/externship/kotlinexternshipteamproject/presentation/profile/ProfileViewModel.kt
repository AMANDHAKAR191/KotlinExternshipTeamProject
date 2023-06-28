package com.externship.kotlinexternshipteamproject.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.externship.kotlinexternshipteamproject.domain.model.Budget
import com.externship.kotlinexternshipteamproject.domain.model.Response.Loading
import com.externship.kotlinexternshipteamproject.domain.model.Response.Success
import com.externship.kotlinexternshipteamproject.domain.repository.ProfileRepository
import com.externship.kotlinexternshipteamproject.domain.repository.RevokeAccessResponse
import com.externship.kotlinexternshipteamproject.domain.repository.SignOutResponse
import com.externship.kotlinexternshipteamproject.domain.use_cases.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: ProfileRepository,
    private val authUseCases: AuthUseCases
) : ViewModel() {
    val displayName get() = repo.displayName
    val photoUrl get() = repo.photoUrl

    var _budgetAmount = mutableStateOf(
        BudgetTextFieldState(
            hint = "Budget(In Rupees)"
        )
    )
    var budgetAmount: State<BudgetTextFieldState> = _budgetAmount

    var _isLoading = mutableStateOf(
        BudgetTextFieldState(
            isLoadingVisible = false
        )
    )
    var isLoading: State<BudgetTextFieldState> = _isLoading


    var signOutResponse by mutableStateOf<SignOutResponse>(Success(false))
        private set
    var revokeAccessResponse by mutableStateOf<RevokeAccessResponse>(Success(false))
        private set

    init {
        viewModelScope.launch {
            authUseCases.getBudgetUseCase.invoke().collect {
                _budgetAmount.value = budgetAmount.value.copy(
                    amount = it.totalBudgetAmount.toString()
                )
            }
        }
    }

    fun signOut() = viewModelScope.launch {
        signOutResponse = Loading
        signOutResponse = repo.signOut()
    }

    fun revokeAccess() = viewModelScope.launch {
        revokeAccessResponse = Loading
        revokeAccessResponse = repo.revokeAccess()
    }

    fun onEvent(event: AddEditBudgetEvent) {
        when (event) {
            is AddEditBudgetEvent.ChangeBudgetAmount -> {
                _budgetAmount.value = budgetAmount.value.copy(
                    amount = event.value,
                    hint = "Budget"
                )
            }

            is AddEditBudgetEvent.SaveBudget -> {
                viewModelScope.launch {
                    _isLoading.value = isLoading.value.copy(
                        isLoadingVisible = true
                    )
                    authUseCases.saveBudgetUseCase(
                        Budget(
                            totalBudgetAmount = budgetAmount.value.amount.toFloat()
                        )
                    ).collect {
                        if (it) {
                            println("isLoading.value.isLoadingVisible: ${isLoading.value.isLoadingVisible}")
                            println("Budget Saved")
                            _isLoading.value = isLoading.value.copy(
                                isLoadingVisible = false
                            )
                        } else {
                            _isLoading.value = isLoading.value.copy(
                                isLoadingVisible = false
                            )
                            println("Failed to Save Budget")
                        }
                    }
                }
            }
        }
    }

}