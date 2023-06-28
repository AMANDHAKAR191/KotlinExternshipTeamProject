package com.externship.kotlinexternshipteamproject.presentation.all_expense_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.externship.kotlinexternshipteamproject.domain.model.Expense
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.AddEditExpenseEvent
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.AddEditExpenseViewModel
import com.externship.kotlinexternshipteamproject.presentation.all_expense_screen.componants.ExpanseItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AllExpenseScreen(
    viewModel: AllExpenseScreenViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
    navigateToFilterByTagScreen: () -> Unit
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    var chipExpanseValueSelected by remember {
        mutableStateOf(true)
    }
    var chipIncomeValueSelected by remember {
        mutableStateOf(false)
    }
    val openDialog = remember { mutableStateOf(false) }
    val itemToDelete = remember { mutableStateOf<Expense?>(null) }
    println("openDialog: ${openDialog.value}")
    if (openDialog.value) {  //ask confirmation from user to delete the expanse
        AlertDialog(
            title = { Text(text = "Alert") },
            text = { Text(text = "This expanse will deleted permanently. Do you still want to delete?") },
            onDismissRequest = {
                openDialog.value = false
            },
            confirmButton = {
                Text(text = "Yes,delete",
                    modifier = Modifier.clickable {
                        itemToDelete.value?.let {
                            viewModel.onEvent(
                                AddEditExpenseEvent.DeleteExpense(
                                    it
                                )
                            )
                        }
                        openDialog.value = false
                    })
            },
            dismissButton = {
                Text(text = "No",
                    modifier = Modifier.clickable {
                        println("dismissButton")
                        openDialog.value = false
                    })
            }
        )
    }
    LaunchedEffect(key1 = viewModel.eventFlow) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is AddEditExpenseViewModel.UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(event.message)
                }

                else -> {}
            }

        }
    }

    if (chipExpanseValueSelected) {
        viewModel.onEvent(AddEditExpenseEvent.GetExpensesFilteredByType("Expense"))
    } else if (chipIncomeValueSelected) {
        viewModel.onEvent(AddEditExpenseEvent.GetExpensesFilteredByType("Income"))
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "All Transactions") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(),
                navigationIcon = {
                    IconButton(onClick = { navigateToHomeScreen() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "back")
                    }
                }
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                InputChip(selected = chipExpanseValueSelected, onClick = {
                    viewModel.onEvent(AddEditExpenseEvent.ChangeExpenseType("Expanse"))
                    chipExpanseValueSelected = !chipExpanseValueSelected
                    if (chipIncomeValueSelected) {
                        chipIncomeValueSelected = !chipIncomeValueSelected
                    }

                }, label = { Text(text = "Expanse") },
                    modifier = Modifier
                        .padding(all = 10.dp)
                )
                InputChip(selected = chipIncomeValueSelected, onClick = {
                    viewModel.onEvent(AddEditExpenseEvent.ChangeExpenseType("Income"))
                    chipIncomeValueSelected = !chipIncomeValueSelected
                    if (chipExpanseValueSelected) {
                        chipExpanseValueSelected = !chipExpanseValueSelected
                    }
                }, label = { Text(text = "Income") },
                    modifier = Modifier
                        .padding(all = 10.dp)
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp)
            ) {
                items(state.expense) { expanse ->
                    ExpanseItem(
                        expense = expanse,
                        modifier = Modifier,
                        onDeleteClick = {
                            itemToDelete.value = expanse
                            println("check 2")
                            openDialog.value = true
                        },
                        onItemTagClick = { navigateToFilterByTagScreen() },
                        onItemClick = {})
                }
                item {
                    Spacer(modifier = Modifier.height(50.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "This is the end of List")
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    }


}